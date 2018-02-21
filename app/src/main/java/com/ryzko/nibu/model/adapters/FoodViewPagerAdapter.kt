package com.ryzko.nibu.model.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import com.ryzko.nibu.view.fragments.FoodRoutinePageFragment

/**
 * Created by Marcin Ryzko on 14.02.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class FoodViewPagerAdapter(fragmentManager: FragmentManager, private val foodArray: MutableList<FoodRoutineObjectData>) :
        FragmentStatePagerAdapter(fragmentManager) {

    lateinit var sortedList:HashMap<String, MutableList<FoodRoutineObjectData>>
    lateinit var foodList:MutableList<FoodRoutineObjectData>

    class DayData(var date:String, var obj:FoodRoutineObjectData)

    fun updateData(foodData: MutableList<FoodRoutineObjectData>){
        for (obj in foodData){
            val date:String = obj.start_time.substring(0,10);
            sortedList = hashMapOf();
            if(sortedList[date]!=null) sortedList[date] = mutableListOf()
            sortedList[date]?.add(obj)

        }
        val sorted = sortedList.toSortedMap()

    }

    override fun getItem(position: Int): Fragment {
        return FoodRoutinePageFragment.newInstance(foodArray[position])
    }

    override fun getCount(): Int {
        return foodArray.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return foodArray[position].start_time.substring(0,10)
    }
}