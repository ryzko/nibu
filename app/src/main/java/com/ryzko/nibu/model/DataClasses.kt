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