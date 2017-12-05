package com.ryzko.nibu.model.adapters

import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import org.zakariya.stickyheaders.SectioningAdapter

/**
 * Created by Marcin Ryzko on 05.12.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class SimpleFoodRoutinesAdapter :SectioningAdapter() {

    lateinit var sections:ArrayList<Section>
    lateinit var list:MutableList<FoodRoutineObjectData>


    class Section constructor(var date:String, var list: ArrayList<FoodRoutineObjectData>){

    }
}