package com.ryzko.nibu.model.user

import android.content.Context
import com.ryzko.nibu.Nibu
import com.ryzko.nibu.R
import com.ryzko.nibu.model.rest.TokenObjectData
import com.ryzko.nibu.model.rest.UserObjectData

/**
 * Created by Marcin Ryzko on 12.09.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class UserCredentials(val context: Context) {

    val PREFERENCES_CREDENTIALS = "credential_preferences"
    fun load(): Credentials? {

        var credentials: Credentials? = null
        val sharedPref = context.getSharedPreferences(PREFERENCES_CREDENTIALS, Context.MODE_PRIVATE)
        val userId = sharedPref.getInt(context.getString(R.string.credentials_user_id), 0)
        val userEmail = sharedPref.getString(context.getString(R.string.credentials_user_email), null)
        val userName = sharedPref.getString(context.getString(R.string.credentials_user_name), null)
        val userSid = sharedPref.getString(context.getString(R.string.credentials_user_sid), null)
        val tokenType = sharedPref.getString(context.getString(R.string.credentials_token_type), null)
        val tokenExpires = sharedPref.getString(context.getString(R.string.credentials_token_expires), null)
        val tokenAccess = sharedPref.getString(context.getString(R.string.credentials_token_access), null)
        val tokenRefresh = sharedPref.getString(context.getString(R.string.credentials_token_refresh), null)
        val babySid = sharedPref.getString(context.getString(R.string.credentials_selected_baby_sid), null)
        if (userSid != null && tokenRefresh != null) {
            val userObjectData = UserObjectData(userId, userName, userEmail, userSid)
            val tokenObjectData = TokenObjectData(tokenType, tokenExpires, tokenAccess, tokenRefresh)
            credentials = Credentials(userObjectData, tokenObjectData, babySid)
        }
        return credentials
    }

    fun save(credentials: Credentials) {

        val sharedPref = context.getSharedPreferences(PREFERENCES_CREDENTIALS, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt(context.getString(R.string.credentials_user_id), credentials.userObject.id)
            putString(context.getString(R.string.credentials_user_email), credentials.userObject.email)
            putString(context.getString(R.string.credentials_user_name), credentials.userObject.name)
            putString(context.getString(R.string.credentials_user_sid), credentials.userObject.sid)
            putString(context.getString(R.string.credentials_token_type), credentials.refreshToken.token_type)
            putString(context.getString(R.string.credentials_token_expires), credentials.refreshToken.expires_in)
            putString(context.getString(R.string.credentials_token_access), credentials.refreshToken.access_token)
            putString(context.getString(R.string.credentials_token_refresh), credentials.refreshToken.refresh_token)
            putString(context.getString(R.string.credentials_selected_baby_sid), credentials.selectedBabySid)
            apply()
        }
    }
}