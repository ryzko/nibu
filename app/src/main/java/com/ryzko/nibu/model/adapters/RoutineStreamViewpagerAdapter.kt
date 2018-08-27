package com.ryzko.nibu.model.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.text.format.DateUtils
import android.view.ViewGroup
import com.ryzko.nibu.model.rest.routines.BaseRoutineObjectData
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import com.ryzko.nibu.view.fragments.RoutineStreamPageFragment
import org.joda.time.format.DateTimeFormat
import java.util.*
import org.joda.time.DateTime



/**
 * Created by Marcin Ryzko on 14.02.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class RoutineStreamViewpagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {
    var cal = Calendar.getInstance()!!

    var fmt = DateTimeFormat.forPattern("dd MMM")
    lateinit var sortedList:HashMap<String, MutableList<BaseRoutineObjectData>>
    var foodList:MutableList<DayData> = mutableListOf()

    class DayData(var date:String, var obj: MutableList<BaseRoutineObjectData>)

    fun addData(foodData: MutableList<BaseRoutineObjectData>){
        sortedList = hashMapOf();
        for (obj in foodData){
            val date:String = (obj as FoodRoutineObjectData).start_time.substring(0,10);

            if(sortedList[date]==null) {
                sortedList[date] = mutableListOf()
            }
            sortedList[date]?.add(obj)

        }
        val sorted = sortedList.toSortedMap()

        for ((key, value) in sorted) {
            val obj = DayData(key,value)
            foodList.add(obj)
        }
        //foodList.reverse();
        notifyDataSetChanged()

    }

    override fun getItem(position: Int): Fragment {
        var res:RoutineStreamPageFragment = RoutineStreamPageFragment.newInstance()
        res.updateData(foodList[position])
        return res
    }

    override fun getCount(): Int {
        return foodList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        val date = DateTime.parse(foodList[position].date, DateTimeFormat.forPattern("yyyy-MM-dd"))
        return DateUtils.getRelativeTimeSpanString(
                date.millis,
                DateTime.now().millis,
                DateUtils.DAY_IN_MILLIS,
                DateUtils.FORMAT_SHOW_DATE).toString()
        //return foodList[position].date
    }
}