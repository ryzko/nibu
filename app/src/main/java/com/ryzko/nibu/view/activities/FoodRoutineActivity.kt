package com.ryzko.nibu.view.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ryzko.nibu.R
import com.ryzko.nibu.model.adapters.FoodRoutinesListAdapter
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import kotlinx.android.synthetic.main.activity_food_routine.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import org.zakariya.stickyheaders.StickyHeaderLayoutManager



class FoodRoutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_routine)
        val list:MutableList<FoodRoutineObjectData> = mutableListOf(
                FoodRoutineObjectData(start_time = "2014-07-07 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-07 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-07 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-08 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-08 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-08 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-08 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-08 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-08 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-08 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-10 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-10 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-10 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-10 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-10 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-10 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-10 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-10 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-13 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-13 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-13 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-13 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-13 18:20"),
                FoodRoutineObjectData(start_time = "2014-07-13 18:20"))

        recyclerview_food_routines.layoutManager = StickyHeaderLayoutManager()
        recyclerview_food_routines.adapter = FoodRoutinesListAdapter(list)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
