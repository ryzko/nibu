package com.ryzko.nibu.model.user

import com.ryzko.nibu.model.rest.BabyObjectData
import com.ryzko.nibu.model.rest.TokenObjectData
import com.ryzko.nibu.model.rest.UserObjectData

/**
 * Created by Marcin Ryzko on 19.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

object UserData{

    lateinit var userObj:UserObjectData
    lateinit var tokenObj:TokenObjectData
    lateinit var selectedBaby: BabyObjectData
    lateinit var babyList:MutableList<BabyObjectData>
    lateinit var credentials: Credentials

    var selectedBabySid:String = ""
    var activityDataService: ActivityRoutineDataService = ActivityRoutineDataService()


    /*companion object {
        lateinit var instance: UserData
            private set
    }*/
}