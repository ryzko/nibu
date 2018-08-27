package com.ryzko.nibu.model.api

import android.content.Context
import com.ryzko.nibu.Nibu
import com.ryzko.nibu.model.rest.*
import com.ryzko.nibu.model.rest.routines.ActivityRoutineObjectData
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import com.ryzko.nibu.model.user.UserData
import io.reactivex.Observable

/**
 * Created by Marcin Ryzko on 18.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

object ApiManager {

    private val service = NibuApi().service
    private var userData:UserData = UserData
    private val PREFIX:String = "Bearer "


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
        return service.addBaby(PREFIX + userData.tokenObj.access_token, babyObj.name, babyObj.birth_date, babyObj.avatar).map {
            result -> BabyObjectData(result.id, result.sid, result.parent_sid, result.name, result.birth_date, result.avatar)
        }
    }

    fun getAllBabies(): Observable<ArrayList<BabyObjectData>> {
        return service.getAllBabies(PREFIX + userData.tokenObj.access_token)
                .flatMap { t: ArrayList<BabyObjectData> ->  Observable.fromArray(t)}
    }


    fun user(tokenObj:TokenObjectData): Observable<UserObjectData> {
        return service.user(PREFIX + tokenObj.access_token).map { result ->
            UserObjectData(result.id, result.name, result.email, result.sid)
        }
    }

    fun addFoodRoutine(foodyObj:FoodRoutineObjectData): Observable<FoodRoutineObjectData> {
        return service.addFoodRoutine(PREFIX + userData.tokenObj.access_token,
                baby_sid = foodyObj.baby_sid,
                type = foodyObj.type,
                breast_side = foodyObj.breast_side,
                breastfeeding_time_minutes = foodyObj.breastfeeding_time_minutes,
                bottle_milk_type = foodyObj.bottle_milk_type,
                start_time = foodyObj.start_time,
                end_time = foodyObj.end_time,
                volume = foodyObj.volume.toString(),
                weight = foodyObj.weight.toString()
                ).map {
            result -> FoodRoutineObjectData(
                id = result.id,
                baby_id = result.baby_id,
                baby_sid = result.baby_sid,
                type = result.type,
                breast_side = result.breast_side,
                breastfeeding_time_minutes = result.breastfeeding_time_minutes,
                bottle_milk_type = result.bottle_milk_type,
                start_time =  result.start_time,
                end_time = result.end_time,
                volume = result.volume,
                weight = result.weight)
        }
    }

    fun getAllFoodRoutines(): Observable<MutableList<FoodRoutineObjectData>> {
        val token:String = PREFIX + userData.tokenObj.access_token
        val babySID:String = userData.selectedBaby.sid
        return service.getAllFoodRoutines(token, babySID)
                .flatMap { t: MutableList<FoodRoutineObjectData> ->  Observable.fromArray(t)}
    }


    fun addActivityRoutine(activityObject:ActivityRoutineObjectData): Observable<ActivityRoutineObjectData> {
        return service.addActivityRoutine(PREFIX + userData.tokenObj.access_token,
                baby_id = activityObject.baby_id,
                baby_sid = activityObject.baby_sid,
                activity_type = activityObject.activity_type,
                activity_start = activityObject.activity_start,
                activity_end = activityObject.activity_end,
                activity_image = activityObject.activity_image,
                activity_comment = activityObject.activity_comment,
                activity_rating = activityObject.activity_rating,
                item_type = activityObject.item_type,
                item_kind = activityObject.item_kind,
                item_weight = activityObject.item_weight,
                item_volume = activityObject.item_volume
        ).map {
            result -> ActivityRoutineObjectData(
                id = result.id,
                baby_id = result.baby_id,
                baby_sid = result.baby_sid,
                activity_type = result.activity_type,
                activity_start = result.activity_start,
                activity_end = result.activity_end,
                activity_image = result.activity_image,
                activity_comment = result.activity_comment,
                activity_rating = result.activity_rating,
                item_type = result.item_type,
                item_kind = result.item_kind,
                item_weight = result.item_weight,
                item_volume = result.item_volume,
                created_at = result.created_at,
                updated_at = result.updated_at)
        }
    }

    fun getAllActivityRoutines(): Observable<MutableList<ActivityRoutineObjectData>> {
        val token:String = PREFIX + userData.tokenObj.access_token
        val babySID:String = userData.selectedBaby.sid
        return service.getActivityRoutines(token, babySID)
                .flatMap { t: MutableList<ActivityRoutineObjectData> ->  Observable.fromArray(t)}
    }

    fun getActivityRoutinesByDay(): Observable<MutableList<GroupedByDayObject>> {
        val token:String = PREFIX + userData.tokenObj.access_token
        val babySID:String = userData.selectedBaby.sid
        return service.getActivityRoutinesByDay(token, babySID)
                .flatMap {
                    t: MutableList<GroupedByDayObject> ->
                    Observable.fromArray(t)}
    }




}

