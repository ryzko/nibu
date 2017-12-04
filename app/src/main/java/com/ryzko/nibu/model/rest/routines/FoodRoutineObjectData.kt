package com.ryzko.nibu.model.rest.routines

/**
 * Created by Marcin Ryzko on 26.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

data class FoodRoutineObjectData (val id:Int = 0, //breast, solid, bottle
                                  val baby_id:Int = 0, //breast, solid, bottle
                                  val baby_sid:String = "", //breast, solid, bottle
                                  val type:String="solid", //breast, solid, bottle
                                  val breast_side:String="left", //left, right
                                  val breastfeeding_time_minutes:Int = 74, //min?sec?
                                  val bottle_milk_type:String="breast",//breast, formula
                                  val start_time:String="2014-07-07 18:20", //2017-11-25 14:20
                                  val end_time:String="2014-07-07 18:30", //2017-11-25 14:50
                                  val volume:Int=150,//150 ml
                                  val weight:Int=130) //120 g