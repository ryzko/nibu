package com.ryzko.nibu.model.rest.routines

/**
 * Created by Marcin Ryzko on 26.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class FoodRoutineObjectData(id:Int=0, baby_id:Int=0, baby_sid:String="", start_time:String, end_time:String="2014-07-07 18:30",
                            val type:String="solid", //breast, solid, bottle
                            val breast_side:String="left", //left, right
                            val breastfeeding_time_minutes:Int = 74, //min?sec?
                            val bottle_milk_type:String="breast",//breast, formula
                            val volume:Int=150,//150 ml
                            val weight:Int=130) : BaseRoutineObjectData(id, baby_id, baby_sid, start_time, end_time){



    fun time(): String {return start_time.substring(12,16)}
} //120 g