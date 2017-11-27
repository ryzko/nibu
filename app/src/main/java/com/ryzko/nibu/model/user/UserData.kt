package com.ryzko.nibu.model.user

import android.content.Context
import com.ryzko.nibu.model.rest.TokenObjectData
import com.ryzko.nibu.model.rest.UserObjectData

/**
 * Created by Marcin Ryzko on 19.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class UserData private constructor(context: Context) {

    var userObj:UserObjectData? = null;
    var tokenObj:TokenObjectData? = null

    companion object {
        private var instance: UserData? = null
        fun getInstance(context: Context): UserData {
            if(instance == null) {
                instance = UserData(context)
            }

            return instance!!
        }
    }
}