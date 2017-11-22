package com.ryzko.nibu.model.api

import com.google.gson.JsonParser
import retrofit2.HttpException

/**
 * Created by Marcin Ryzko on 18.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class ApiError constructor(error: Throwable) {
    var message = "An error occurred"

    init {
        if (error is HttpException) {
            val errorJsonString = error.response()
                    .errorBody()?.string()
            this.message = JsonParser().parse(errorJsonString)
                    .asJsonObject["message"]
                    .asString
        } else {
            this.message = error.message ?: this.message
        }
    }
}