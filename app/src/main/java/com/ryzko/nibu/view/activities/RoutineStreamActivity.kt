package com.ryzko.nibu.view.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.nshmura.recyclertablayout.RecyclerTabLayout
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ryzko.nibu.R
import com.ryzko.nibu.model.adapters.RoutineStreamViewpagerAdapter
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.rest.routines.BaseRoutineObjectData
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import com.ryzko.nibu.view.fragments.RoutineStreamPageFragment.OnFragmentInteractionListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_routine_stream.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class RoutineStreamActivity : AppCompatActivity(), OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var recyclerTabLayout: SmartTabLayout
    private lateinit var pagerAdapter: RoutineStreamViewpagerAdapter
    private lateinit var viewPager: ViewPager


    private var foodData:MutableList<BaseRoutineObjectData> = mutableListOf(
            FoodRoutineObjectData( start_time = "2018-08-23 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-22 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-21 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-23 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-23 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-20 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-19 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-18 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-17 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-16 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-15 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-14 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-13 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2018-08-12 19:30", weight = 1),
            FoodRoutineObjectData( start_time = "2011-07-11 19:30", weight = 1)
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_routine_stream)
        viewPager = this.findViewById(R.id.viewPager)
        pagerAdapter = RoutineStreamViewpagerAdapter(supportFragmentManager)
        pagerAdapter.addData(foodData)
        viewPager.adapter = pagerAdapter

        recyclerTabLayout = this.findViewById(R.id.recyclerTabLayout)
        recyclerTabLayout.setViewPager(viewPager)
        viewPager.setCurrentItem(foodData.size-1,false)

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


    private fun onFoodRoutineResponse(list:MutableList<FoodRoutineObjectData>){
        //foodData = list


    }

    private fun onRequestFailure(error: Throwable) {

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
