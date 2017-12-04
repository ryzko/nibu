package com.ryzko.nibu.view.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ryzko.nibu.R
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import kotlinx.android.synthetic.main.activity_food_routine_breastfeeding_details.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*


class FoodRoutineBreastfeedingDetailsActivity : AppCompatActivity() {

    var itemSID:String? =""
    var items:List<FoodRoutineObjectData>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_routine_breastfeeding_details)
        //if(ifUpdate())
            initDateTime()

        breast_side_switcher.setOnClickListener {
            if (breast_side_switcher.isChecked){
                caption_right.setTextColor(resources.getColor(R.color.nibu_accent))
                caption_left.setTextColor(resources.getColor(R.color.nibu_gray_dark))
                //caption_right.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
                //caption_left.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
            }else{
                caption_left.setTextColor(resources.getColor(R.color.nibu_accent))
                caption_right.setTextColor(resources.getColor(R.color.nibu_gray_dark))
                //caption_right.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
               // caption_left.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            }
        }

    }

    fun initDateTime(){
        val currentDate = Calendar.getInstance().time
        val simpleDateFormat = java.text.SimpleDateFormat("dd-MM-yyyy")
        val simpleTimeFormat = java.text.SimpleDateFormat("HH:mm")
        val formattedCurrentDate = simpleDateFormat.format(currentDate)
        val formattedCurrentTime = simpleTimeFormat.format(currentDate)
        textview_date.setText(formattedCurrentDate)
        textview_breast_starttime.setText(formattedCurrentTime)
    }

    fun ifUpdate():Boolean{
        itemSID = intent.getStringExtra("ITEM_SID")
        return itemSID!=null
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
