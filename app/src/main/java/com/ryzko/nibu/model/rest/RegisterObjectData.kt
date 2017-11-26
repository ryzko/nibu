package com.ryzko.nibu.model.rest

/**
 * Created by Marcin Ryzko on 26.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

data class RegisterObjectData (
        val name:String,
        val id:Int,
        val email:String,
        val password:String,
        val passwordConfirmation:String,
        val clientId:Int = 1,
        val clientSecret:String = "UBiRnwjMXFVT9Ao0fg2JNhKdbcahzHBu240w23zK",
        val granType:String = "password"
)