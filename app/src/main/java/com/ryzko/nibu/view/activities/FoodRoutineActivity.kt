package com.ryzko.nibu.view.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewParent
import android.widget.LinearLayout
import android.widget.Toast
import com.ryzko.nibu.R
import com.ryzko.nibu.model.adapters.FoodRoutinesListAdapter
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.rest.BabyObjectData
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import org.zakariya.stickyheaders.StickyHeaderLayoutManager
import com.nshmura.recyclertablayout.RecyclerTabLayout
import com.ryzko.nibu.model.adapters.FoodViewPagerAdapter
import com.ryzko.nibu.view.fragments.FoodRoutinePageFragment
import com.ryzko.nibu.view.fragments.FoodRoutinePageFragment.OnFragmentInteractionListener
import kotlinx.android.synthetic.main.activity_food_routine.*


class FoodRoutineActivity : AppCompatActivity(), OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var recyclerTabLayout: RecyclerTabLayout
    private lateinit var pagerAdapter: FoodViewPagerAdapter
    private lateinit var viewPager: ViewPager


    var foodData:MutableList<FoodRoutineObjectData> = mutableListOf(
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30", weight = 1)
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_food_routine)
        viewPager = findViewById(R.id.viewPager)
        pagerAdapter = FoodViewPagerAdapter(supportFragmentManager, foodData)
        viewPager.adapter = pagerAdapter

        recyclerTabLayout = findViewById(R.id.recyclerTabLayout)
        recyclerTabLayout.setUpWithViewPager(viewPager)

        menu_breast.setOnClickListener {
            startActivity(Intent(this, FoodRoutineBreastfeedingDetailsActivity::class.java))
        }

        //getFoodItems()

    }


    fun getFoodItems(){
        ApiManager
                .getAllFoodRoutines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: MutableList<FoodRoutineObjectData> ->
                    if (list != null) onFoodRoutineResponse(list)
                }, { error: Throwable? ->
                    if (error != null) onRequestFailure(error)

                })
    }


    fun onFoodRoutineResponse(list:MutableList<FoodRoutineObjectData>){
        //foodData = list


    }

    private fun onRequestFailure(error: Throwable) {

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
