package com.ryzko.nibu.view.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import com.ryzko.nibu.R
import com.ryzko.nibu.model.adapters.FoodRoutinesListAdapter
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.rest.BabyObjectData
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_food_routine.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import org.zakariya.stickyheaders.StickyHeaderLayoutManager



class FoodRoutineActivity : AppCompatActivity() {

    var foodData:MutableList<FoodRoutineObjectData> = mutableListOf(FoodRoutineObjectData( start_time = "2011-07-07 19:30"),
            FoodRoutineObjectData( start_time = "2011-07-07 19:30"),
            FoodRoutineObjectData( start_time = "2012-07-07 19:30"),
            FoodRoutineObjectData( start_time = "2012-07-07 19:30"),
            FoodRoutineObjectData( start_time = "2012-07-07 19:30"),
            FoodRoutineObjectData( start_time = "2012-07-07 19:30"),
            FoodRoutineObjectData( start_time = "2012-07-07 19:30"),
            FoodRoutineObjectData( start_time = "2012-07-07 19:30"),
            FoodRoutineObjectData( start_time = "2012-07-07 19:30"),
            FoodRoutineObjectData( start_time = "2012-07-07 19:30"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_routine)
        recyclerview_food_routines.layoutManager = StickyHeaderLayoutManager()
        var adapter = FoodRoutinesListAdapter(foodData)
        recyclerview_food_routines.adapter = adapter
        (recyclerview_food_routines.adapter as FoodRoutinesListAdapter).notifyAllSectionsDataSetChanged()

        swipe_layout.setOnRefreshListener {
            onRefreshList()
        }

        menu_breast.setOnClickListener {
            startActivity(Intent(this, FoodRoutineBreastfeedingDetailsActivity::class.java))
        }

        getFoodItems()

    }

    fun onRefreshList(){
        getFoodItems()
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

        swipe_layout.isRefreshing = false
    }

    private fun onRequestFailure(error: Throwable) {

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
