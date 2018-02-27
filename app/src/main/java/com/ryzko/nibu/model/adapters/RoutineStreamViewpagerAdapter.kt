package com.ryzko.nibu.model.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.ryzko.nibu.model.rest.routines.BaseRoutineObjectData
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import com.ryzko.nibu.view.fragments.RoutineStreamPageFragment

/**
 * Created by Marcin Ryzko on 14.02.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class RoutineStreamViewpagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

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
        foodList.reverse();
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
        return foodList[position].date
    }
}