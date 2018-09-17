package com.ryzko.nibu.model.user

import com.ryzko.nibu.model.rest.TokenObjectData
import com.ryzko.nibu.model.rest.UserObjectData

/**
 * Created by Marcin Ryzko on 13.09.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

data class Credentials(var userObject:UserObjectData, var refreshToken: TokenObjectData, var selectedBabySid:String)