package com.ryzko.nibu.model.rest.routines

/**
 * Created by Marcin Ryzko on 26.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

data class FoodRoutineObjectData (val id:Int, //breast, solid, bottle
                                  val baby_id:Int, //breast, solid, bottle
                                  val baby_sid:String, //breast, solid, bottle
                                  val type:String, //breast, solid, bottle
                                  val breast_side:String, //left, right
                                  val breastfeeding_time_minutes:String, //min?sec?
                                  val bottle_milk_type:String,//breast, formula
                                  val start_time:String, //2017-11-25 14:20
                                  val end_time:String, //2017-11-25 14:50
                                  val volume:Int,//150 ml
                                  val weight:Int) //120 g