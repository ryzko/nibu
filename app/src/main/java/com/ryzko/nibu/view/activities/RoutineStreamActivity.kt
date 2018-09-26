package com.ryzko.nibu.view.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.ryzko.nibu.R
import com.ryzko.nibu.model.activities.ActivityDayData
import com.ryzko.nibu.model.adapters.RoutineStreamViewpagerAdapter
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.events.ActivitiesResultEvent
import com.ryzko.nibu.model.events.RoutinesScrollEvent
import com.ryzko.nibu.model.events.registerInBus
import com.ryzko.rxminibus.RxMiniBus
import com.ryzko.nibu.model.rest.routines.ActivityRoutineObjectData
import com.ryzko.nibu.model.user.UserData
import com.ryzko.nibu.model.utils.DateParseUtils
import com.ryzko.nibu.view.fragments.RoutineStreamPageFragment.OnFragmentInteractionListener
import com.ryzko.rxminibus.registerWith
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_routine_stream.*
import org.joda.time.DateTime
import ru.whalemare.sheetmenu.SheetMenu
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*


class RoutineStreamActivity : AppCompatActivity(), OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private val ADD_ACTIVITY_REQUEST = 1
    private var selectedDate = DateParseUtils.getString(DateTime.now(), DateParseUtils.YYYYMMDD_HHMMSS)

    private lateinit var selectedActivities: ActivityDayData
    private lateinit var pagerAdapter: RoutineStreamViewpagerAdapter

    private var pageSelected = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_stream)
        RxMiniBus.observe<ActivitiesResultEvent>()
                .subscribe {
                    onGetActivities(it.list)
                }.registerWith(this)

        UserData.activityDataService.getAllActivitiesRoutines()


        button_select_date.setOnClickListener {
            button_select_date.isEnabled = false
            selectDate()
        }

        val fontTypeFace = Typeface.createFromAsset(assets,
                "fonts/GoogleSans-Regular.ttf")

        for (i in 0..pagerstrip_activities.childCount) {
            val nextChild = pagerstrip_activities.getChildAt(i)
            if (nextChild is TextView) nextChild.typeface = fontTypeFace

        }

        buildAddButton()


    }

    private fun buildAddButton() {
        fab_add_activity.setOnClickListener { _ ->
            SheetMenu(
                    titleId = R.string.menu_add_daily_routine_title,
                    click = MenuItem.OnMenuItemClickListener { selectDailyRoutine(it.itemId) },
                    menu = R.menu.menu_add_daily_routine
            ).show(this)
        }
    }


    private fun selectDailyRoutine(selectionId: Int): Boolean {

        when (selectionId) {
            R.id.menu_add_breastfeeding -> {
                val intent = Intent(this, ActivityBreastFeedDetails::class.java)
                intent.putExtra("Date", selectedDate)
                startActivityForResult(intent, ADD_ACTIVITY_REQUEST)
            }

        }
        return true
    }


    private fun selectDate() {
        val calendar = Calendar.getInstance(TimeZone.getDefault())


        val dpd = DatePickerDialog(ContextThemeWrapper(this, R.style.PickerDialog), DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val date = DateTime(calendar.timeInMillis)

            viewpager_activities.setCurrentItem(pagerAdapter.getIndexByDate(date), true)
            button_select_date.isEnabled = true

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))

        with(dpd.datePicker) {
            maxDate = calendar.timeInMillis
            minDate = DateParseUtils.getDateTime(UserData.selectedBaby.birth_date, DateParseUtils.YYYYMMDD_HHMMSS).millis
        }


        dpd.show()
    }


    private fun getAllActivitiesRoutines() {
        ApiManager.getAllActivityRoutines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: MutableList<ActivityRoutineObjectData> ->
                    onGetActivities(list)
                }) { error: Throwable? ->
                    if (error != null) onRequestFailure(error)

                }
    }

    private fun onGetActivities(activityList: MutableList<ActivityRoutineObjectData>) {
        preparePagerAdapter(activityList)

        UserData.activityDataService.addList(activityList)

        when (pageSelected) {
            -1 -> viewpager_activities.setCurrentItem(pagerAdapter.datesList.size, false)
            else -> {
                viewpager_activities.setCurrentItem(pageSelected, false)
            }
        }

        viewpager_activities.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                val data: ActivityDayData? = pagerAdapter.findActivityByDate(position)
                selectedDate = DateParseUtils.getString(pagerAdapter.datesList[position], DateParseUtils.YYYYMMDD_HHMMSS)
                val date = DateParseUtils.getDateTime(selectedDate, DateParseUtils.YYYYMMDD_HHMMSS)
                text_page_date.text = DateParseUtils.getString(date, DateParseUtils.ddMMMyyyy)
                data?.let {
                    selectedActivities = data
                }

                pageSelected = position
                setPageScrollListener()
            }
        })

    }

    fun setPageScrollListener() {
        RxMiniBus.unregister(this)

        RxMiniBus.observe<RoutinesScrollEvent>().subscribe {
            when (it.hideFab) {
                true -> {
                    if (fab_add_activity.fabTextVisibility == View.VISIBLE) fab_add_activity.fabTextVisibility = View.GONE
                }
                else -> fab_add_activity.fabTextVisibility = View.VISIBLE
            }
        }.registerWith(this)
    }

    private fun preparePagerAdapter(activityList: MutableList<ActivityRoutineObjectData>) {
        pagerAdapter = RoutineStreamViewpagerAdapter(supportFragmentManager)
        pagerAdapter.initData(UserData.activityDataService)
        viewpager_activities.adapter = pagerAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            Activity.RESULT_OK -> {
                getAllActivitiesRoutines()
            }
        }
    }

    private fun onRequestFailure(error: Throwable) {

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onDestroy() {
        super.onDestroy()
        RxMiniBus.unregister(this)
    }


}

