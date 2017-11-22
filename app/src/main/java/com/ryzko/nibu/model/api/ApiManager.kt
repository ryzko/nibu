package com.ryzko.nibu.model.api

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.StructuredName.PREFIX
import com.ryzko.nibu.model.*
import com.ryzko.nibu.model.user.UserData
import io.reactivex.Observable

/**
 * Created by Marcin Ryzko on 18.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class ApiManager private constructor(context: Context) {

    private val service = NibuApi().service
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

    fun register(registerObj:RegisterObject): Observable<TokenObject> {
        return service.register(registerObj.name, registerObj.email, registerObj.password, registerObj.passwordConfirmation, registerObj.clientId, registerObj.clientSecret, registerObj.granType).map {
            result -> TokenObject(result.token_type, result.expires_in, result.access_token, result.refresh_token)
        }
    }

    fun login(loginObj:LoginObject): Observable<TokenObject> {
        return service.login(loginObj.email, loginObj.password, loginObj.clientId, loginObj.clientSecret, loginObj.granType).map {
            result -> TokenObject(result.token_type, result.expires_in, result.access_token, result.refresh_token)
        }
    }

    fun addBaby(babyObj:BabyObject): Observable<BabyResponse> {
        return service.addBaby(PREFIX + UserData.getInstance(this.context).tokenObj!!.access_token, babyObj.name, babyObj.birth_date, babyObj.avatar).map {
            result -> BabyResponse(result.id, result.sid, result.parent_sid, result.name, result.birth_date, result.avatar)
        }
    }

    fun getAllBabies(): Observable<ArrayList<BabyResponse>> {
        return service.getAllBabies(PREFIX + UserData.getInstance(this.context).tokenObj!!.access_token)
                .flatMap { t: ArrayList<BabyResponse> ->  Observable.fromArray(t)}
    }


    fun user(tokenObj:TokenObject): Observable<User> {
        return service.user(PREFIX + tokenObj.access_token).map { result ->
            User(result.id, result.name, result.email, result.sid)
        }
    }



}

