package com.ryzko.nibu.model.rest.routines

/**
 * Created by Marcin Ryzko on 25.02.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

open class BaseRoutineObjectData (  val id:Int = 0, //id
                                    val baby_id:Int = 0, //breast, solid, bottle
                                    val baby_sid:String = "", //breast, solid, bottle
                                    val start_time:String="2014-07-07 18:20", //2017-11-25 14:20
                                    val end_time:String="2014-07-07 18:30" //2017-11-25 14:50
                                    )