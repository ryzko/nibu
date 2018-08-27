package com.ryzko.nibu.model.rest.routines

/**
 * Created by Marcin Ryzko on 26.08.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class ActivityRoutineObjectData(val id:Int,
                                val baby_id:Int,
                                val baby_sid:String,
                                val activity_type:String,
                                val activity_start:String,
                                val activity_end:String,
                                val activity_image:String,
                                val activity_comment:String,
                                val activity_rating:Int,
                                val item_type:String,
                                val item_kind:String,
                                val item_weight:String,
                                val item_volume:String,
                                val created_at:String,
                                val updated_at:String) {
}