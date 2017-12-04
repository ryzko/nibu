package com.ryzko.nibu.model.user

import android.content.Context
import com.ryzko.nibu.Nibu
import com.ryzko.nibu.model.adapters.FoodRoutinesListAdapter
import com.ryzko.nibu.model.rest.BabyObjectData
import com.ryzko.nibu.model.rest.TokenObjectData
import com.ryzko.nibu.model.rest.UserObjectData
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData

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
    lateinit var sectionedFoodList:MutableList<FoodRoutinesListAdapter.Section>

    /*companion object {
        lateinit var instance: UserData
            private set
    }*/
}