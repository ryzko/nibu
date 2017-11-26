package com.ryzko.nibu.model.rest

/**
 * Created by Marcin Ryzko on 26.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

data class TokenObjectData (val token_type:String, val expires_in:String, val access_token:String, val refresh_token:String)