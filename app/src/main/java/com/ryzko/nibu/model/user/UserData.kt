package com.ryzko.nibu.model.user

import android.content.Context
import com.ryzko.nibu.model.TokenObject
import com.ryzko.nibu.model.User

/**
 * Created by Marcin Ryzko on 19.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class UserData private constructor(context: Context) {

    var userObj:User? = null;
    var tokenObj:TokenObject? = null

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