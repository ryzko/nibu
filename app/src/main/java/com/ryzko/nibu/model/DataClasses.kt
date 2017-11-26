package com.ryzko.nibu.model

import java.util.*

/**
 * Created by mryzk on 16.11.2017.
 */
data class RegisterObject(
        val name:String,
        val id:Int,
        val email:String,
        val password:String,
        val passwordConfirmation:String,
        val clientId:Int = 1,
        val clientSecret:String = "UBiRnwjMXFVT9Ao0fg2JNhKdbcahzHBu240w23zK",
        val granType:String = "password"
)

data class LoginObject(
        val email:String,
        val password:String,
        val clientId:Int = 1,
        val clientSecret:String = "UBiRnwjMXFVT9Ao0fg2JNhKdbcahzHBu240w23zK",
        val granType:String = "password"
)

data class TokenObject(val token_type:String, val expires_in:String, val access_token:String, val refresh_token:String)

data class User(val id:Int, val name:String, val email:String, val sid:String)

data class BabyObject(val name:String, val birth_date:String, val avatar:String)
data class BabyResponse(val id:Int,val sid:String, val parent_sid:String, val name:String, val birth_date:String, val avatar:String)
data class RoutineFoodObject(val type:String, //breast, solid, bottle
                             val breast_side:String, //left, right
                             val breast_time:String, //min?sec?
                             val bottle_milk_type:String,//breast, formula
                             val start_time:String, //2017-11-25 14:20
                             val end_time:String, //2017-11-25 14:50
                             val volume:Int,//150 ml
                             val weight:Int) //120 g

data class RoutineFoodResult(val id:Int, //breast, solid, bottle
                             val baby_id:Int, //breast, solid, bottle
                             val baby_sid:String, //breast, solid, bottle
                             val type:String, //breast, solid, bottle
                             val breast_side:String, //left, right
                             val breast_time:String, //min?sec?
                             val bottle_milk_type:String,//breast, formula
                             val start_time:String, //2017-11-25 14:20
                             val end_time:String, //2017-11-25 14:50
                             val volume:Int,//150 ml
                             val weight:Int) //120 g
