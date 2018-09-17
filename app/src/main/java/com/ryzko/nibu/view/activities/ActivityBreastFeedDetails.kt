package com.ryzko.nibu.view.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.ryzko.nibu.R
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_breast_feed_detail.*
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.activities.ActivityRoutineType
import com.ryzko.nibu.model.rest.routines.ActivityRoutineObjectData
import com.ryzko.nibu.model.user.UserData
import com.ryzko.nibu.model.utils.DateParseUtils
import com.ryzko.nibu.view.components.BabyDatePickerDialog
import com.ryzko.nibu.view.components.TextViewButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.format.DateTimeFormat


class ActivityBreastFeedDetails : AppCompatActivity() {

    private lateinit var backBtn: ImageButton
    lateinit var toolbar: Toolbar


    private lateinit var onSaveData: (dataObject: ActivityRoutineObjectData) -> Unit


    var startDateTime: DateTime = DateTime()
    var endDateTime: DateTime = DateTime()
    var now:DateTime = DateTime()
    var breastSide = ""
    var comment = ""
    val breastSideData = arrayListOf<String>("left", "right","both")

    var dayString =""

    lateinit var day:DateTime


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breast_feed_detail)
        dayString = intent.getStringExtra("Date").toString()
        day = DateParseUtils.getDateTime(dayString, DateParseUtils.YYYYMMDD_HHMMSS)
        initViews()
    }

    private fun initViews() {
        toolbar = this.findViewById(R.id.toolbar)
        backBtn = this.findViewById(R.id.backButton)
        setupDatesViews()
        setBackButtonListener()
    }

    private fun setupDatesViews() {

        var adapter = ArrayAdapter<String>(this, R.layout.layout_spinner_item, resources.getStringArray(R.array.breast_side))
        spinner_breast_side.adapter = adapter

        text_start_date.setLabel(getString(R.string.activity_start_date))
        text_end_date.setLabel(getString(R.string.activity_end_date))
        text_start_time.setLabel(getString(R.string.activity_start_time))
        text_end_time.setLabel(getString(R.string.activity_end_time))

        text_start_date.setText(DateParseUtils.getString(day, DateParseUtils.ddMMMyyyy))
        text_end_date.setText(DateParseUtils.getString(day, DateParseUtils.ddMMMyyyy))
        text_start_time.setText(DateParseUtils.getString(now, DateParseUtils.HHMM))
        text_end_time.setText(DateParseUtils.getString(now, DateParseUtils.HHMM))


        val dateViews = mutableListOf(text_start_date, text_end_date, text_start_time, text_end_time)
        for (v in dateViews) {

            v.setOnClickListener { k -> onDateViewClicked(k) }
        }

        saveButton.setOnClickListener { k-> saveActivity() }
    }

    private fun saveActivity(){
        val strStartDate = "${text_start_date.getText()} ${text_start_time.getText()}"
        val strEndDate = "${text_end_date.getText()} ${text_end_time.getText()}"
        startDateTime = DateTime.parse(strStartDate, DateTimeFormat.forPattern("dd MMM yyyy HH:mm"))
        endDateTime = DateTime.parse(strEndDate, DateTimeFormat.forPattern("dd MMM yyyy HH:mm"))

        val format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

        val activityStart = format.print(startDateTime)
        val activityEnd = format.print(endDateTime)

        breastSide = spinner_breast_side.selectedItem.toString()
        comment = commentText.text.toString()
        val selectedSpinnerData = breastSideData[spinner_breast_side.selectedItemPosition]

        val item = ActivityRoutineObjectData(baby_id = UserData.selectedBaby.id, baby_sid = UserData.selectedBaby.sid, activity_type = ActivityRoutineType.FOOD, item_type = ActivityRoutineType.BREAST, item_kind = selectedSpinnerData, activity_start = activityStart, activity_end = activityEnd, activity_comment = comment )

        ApiManager.addActivityRoutine(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ activity: ActivityRoutineObjectData? ->
                    if (activity != null) {
                        onSaveActivity()
                    }
                }, { error: Throwable? ->
                    if (error != null) {
                        onRequestFailure()
                    }
                })

    }

    private fun onSaveActivity(){
        val result = Intent()
        setResult(Activity.RESULT_OK, result)
        finish()
    }


    private fun onRequestFailure() {
        val result = Intent()
        setResult(Activity.RESULT_CANCELED, result)
        finish()
    }



    private fun onDateViewClicked(view: View) {
        when (view) {
            text_start_date -> {
                showDatePicker(text_start_date)
            }

            text_end_date -> {
                showDatePicker(text_end_date)
            }

            text_start_time -> {
                showTimePicker(text_start_time)
            }

            text_end_time -> {
                showTimePicker(text_end_time)
            }

        }
    }

    private fun showDatePicker(text: TextViewButton) {
        val calendar = Calendar.getInstance(TimeZone.getDefault())


        BabyDatePickerDialog(ContextThemeWrapper(this, R.style.PickerDialog), DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd MMM yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())

            text.setText(sdf.format(calendar.time))
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()


    }

    private fun showTimePicker(text: TextViewButton) {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        TimePickerDialog(ContextThemeWrapper(this, R.style.PickerDialog), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            val myFormat = "HH:mm"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            text.setText(sdf.format(calendar.time))


        },Calendar.HOUR_OF_DAY,Calendar.MINUTE,true).show()
    }



    private fun setBackButtonListener() {
        backBtn.setOnClickListener {
            this.onBackPressed()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
interface ActivityRoutineOnSaveData {
    fun onActivityRoutineSaveData(activity: ActivityBreastFeedDetails)
}