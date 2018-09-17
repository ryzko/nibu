package com.ryzko.nibu.model.api

import com.ryzko.nibu.model.rest.BabyObjectData
import com.ryzko.nibu.model.rest.GroupedByDayObject
import com.ryzko.nibu.model.rest.TokenObjectData
import com.ryzko.nibu.model.rest.UserObjectData
import com.ryzko.nibu.model.rest.routines.ActivityRoutineObjectData
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by Marcin Ryzko on 16.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */
interface NibuApiService {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password: String,
                 @Field("password_confirmation") passwordConfirmation: String,
                 @Field("client_id") clientID: Int,
                 @Field("client_secret") clientSecret: String,
                 @Field("grant_type") grantType: String
    ): Observable<TokenObjectData>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username: String,
              @Field("password") password: String,
              @Field("client_id") clientID: Int,
              @Field("client_secret") clientSecret: String,
              @Field("grant_type") grantType: String): Observable<TokenObjectData>

    @Headers("Accept: application/json")
    @GET("user")
    fun user(@Header("Authorization") token: String
    ): Observable<UserObjectData>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("babies")
    fun addBaby(
            @Header("Authorization") token: String,
            @Field("username") username: String,
            @Field("birth_date") birth_date: String,
            @Field("avatar") avatar: String): Observable<BabyObjectData>

    @Headers("Accept: application/json")
    @GET("babies")
    fun getAllBabies(
            @Header("Authorization") token: String
    ): Observable<ArrayList<BabyObjectData>>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("babies")
    fun updateBaby(
            @Header("Authorization") token: String,
            @Field("id") id: Int,
            @Field("username") username: String,
            @Field("birth_date") password: String,
            @Field("avatar") clientID: Int
    ): Observable<BabyObjectData>


//ActivitiesRoutines---------------------------------------------------------------------

    @Headers(
            "Accept: application/json",
            "User-Agent: Nibu",
            "Cache-Control: no-cache")
    @FormUrlEncoded
    @POST("activity_routines/{baby_sid}")
    fun addActivityRoutine(
            @Header("Authorization") token: String,
            @Path("baby_sid") baby_sid: String,
            @Field("activity_type") activity_type: String,
            @Field("activity_start") activity_start: String,
            @Field("activity_end") activity_end: String,
            @Field("activity_image") activity_image: String,
            @Field("activity_comment") activity_comment: String,
            @Field("activity_rating") activity_rating: Int,
            @Field("item_type") item_type: String,
            @Field("item_kind") item_kind: String,
            @Field("item_weight") item_weight: String,
            @Field("item_volume") item_volume: String
    ): Observable<ActivityRoutineObjectData>

    @Headers("Accept: application/json")
    @GET("activity_routines/{baby_sid}")
    fun getActivityRoutines(
            @Header("Authorization") token: String,
            @Path("baby_sid") baby_sid: String
    ):Observable<MutableList<ActivityRoutineObjectData>>

    @Headers("Accept: application/json")
    @GET("activity_routines_by_day/{baby_sid}")
    fun getActivityRoutinesByDay(
            @Header("Authorization") token: String,
            @Path("baby_sid") baby_sid: String
    ):Observable<MutableList<MutableList<Any>>>


    @Headers("Accept: application/json")
    @PUT("activity_routines")
    fun updateActivityRoutines(
            @Header("Authorization") token: String,
            @Query("baby_sid") baby_sid: String,
            @Query("id") id: String
    ): Observable<MutableList<ActivityRoutineObjectData>>

}