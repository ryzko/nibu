package com.ryzko.nibu.model.rest.routines

/**
 * Created by Marcin Ryzko on 26.08.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class ActivityRoutineObjectData(val id:Int= 0,
                                val baby_id:Int = 0,
                                val baby_sid:String = "null",
                                val activity_type:String = "null",
                                val activity_start:String = "null",
                                val activity_end:String = "null",
                                val activity_image:String = "null",
                                val activity_comment:String = "null",
                                val activity_rating:Int = 0,
                                val item_type:String = "null",
                                val item_kind:String = "null",
                                val item_weight:String = "null",
                                val item_volume:String = "null",
                                val created_at:String = "null",
                                val updated_at:String = "null") {
}