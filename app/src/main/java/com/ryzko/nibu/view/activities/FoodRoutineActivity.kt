package com.ryzko.nibu.view.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ryzko.nibu.R
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class FoodRoutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_routine)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
