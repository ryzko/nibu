package com.ryzko.nibu.model.api

import android.content.Context
import com.ryzko.nibu.model.rest.*
import com.ryzko.nibu.model.user.UserData
import io.reactivex.Observable

/**
 * Created by Marcin Ryzko on 18.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class ApiManager private constructor(context: Context) {

    private val service = NibuApi().service
    private var userData:UserData = UserData.getInstance(context)
    private val PREFIX:String = "Bearer "
    private val context:Context = context;

    companion object
    {
        private var instance: ApiManager? = null
        fun getInstance(context: Context): ApiManager
        {
            if(instance == null)
            {
                instance = ApiManager(context)
            }

            return instance!!
        }
    }

    fun register(registerObj:RegisterObjectData): Observable<TokenObjectData> {
        return service.register(registerObj.name, registerObj.email, registerObj.password, registerObj.passwordConfirmation, registerObj.clientId, registerObj.clientSecret, registerObj.granType).map {
            result -> TokenObjectData(result.token_type, result.expires_in, result.access_token, result.refresh_token)
        }
    }

    fun login(loginObj:LoginObjectData): Observable<TokenObjectData> {
        return service.login(loginObj.email, loginObj.password, loginObj.clientId, loginObj.clientSecret, loginObj.granType).map {
            result -> TokenObjectData(result.token_type, result.expires_in, result.access_token, result.refresh_token)
        }
    }

    fun addBaby(babyObj:BabyObjectData): Observable<BabyObjectData> {
        return service.addBaby(PREFIX + userData.tokenObj!!.access_token, babyObj.name, babyObj.birth_date, babyObj.avatar).map {
            result -> BabyObjectData(result.id, result.sid, result.parent_sid, result.name, result.birth_date, result.avatar)
        }
    }

    fun getAllBabies(): Observable<ArrayList<BabyObjectData>> {
        return service.getAllBabies(PREFIX + userData.tokenObj!!.access_token)
                .flatMap { t: ArrayList<BabyObjectData> ->  Observable.fromArray(t)}
    }


    fun user(tokenObj:TokenObjectData): Observable<UserObjectData> {
        return service.user(PREFIX + tokenObj.access_token).map { result ->
            UserObjectData(result.id, result.name, result.email, result.sid)
        }
    }



}

