package com.ryzko.nibu.model.api

import com.ryzko.nibu.model.rest.BabyObjectData
import com.ryzko.nibu.model.rest.TokenObjectData
import com.ryzko.nibu.model.rest.UserObjectData
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
    fun register(@Field("name") name:String,
                 @Field("email") email:String,
                 @Field("password") password:String,
                 @Field("password_confirmation") passwordConfirmation:String,
                 @Field("client_id") clientID:Int,
                 @Field("client_secret") clientSecret:String,
                 @Field("grant_type") grantType:String):Observable<TokenObjectData>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username:String,
                 @Field("password") password:String,
                 @Field("client_id") clientID:Int,
                 @Field("client_secret") clientSecret:String,
                 @Field("grant_type") grantType:String):Observable<TokenObjectData>

    @Headers("Accept: application/json")
    @GET("user")
    fun user(@Header("Authorization") token:String):Observable<UserObjectData>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("babies")
    fun addBaby(
            @Header("Authorization") token:String,
            @Field("username") username:String,
            @Field("birth_date") birth_date:String,
            @Field("avatar") avatar:String):Observable<BabyObjectData>

    @Headers("Accept: application/json")
    @GET("babies")
    fun getAllBabies(
            @Header("Authorization") token:String):Observable<ArrayList<BabyObjectData>>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("babies")
    fun updateBaby(
            @Header("Authorization") token:String,
            @Field("id") id:Int,
            @Field("username") username:String,
            @Field("birth_date") password:String,
            @Field("avatar") clientID:Int):Observable<BabyObjectData>

//'baby_id', 'baby_sid', 'type', 'breast_side', 'breastfeeding_time_minutes', 'bottle_milk_type', 'start_time', 'end_time', 'volume', 'weight'
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("food_routines")
    fun addFoodRoutine(
            @Header("Authorization") token:String,
            @Field("baby_sid") baby_sid:String,
            @Field("type") type:String,
            @Field("breast_side") breast_side:String,
            @Field("breastfeeding_time_minutes") breastfeeding_time_minutes:Int,
            @Field("bottle_milk_type") bottle_milk_type:String,
            @Field("start_time") start_time:String,
            @Field("end_time") end_time:String,
            @Field("volume") volume:String,
            @Field("weight") weight:String):Observable<FoodRoutineObjectData>

    @Headers("Accept: application/json")
    @GET("food_routines/{baby_sid}")
    fun getAllFoodRoutines(
            @Header("Authorization") token:String,
            @Path("baby_sid") baby_sid:String):Observable<MutableList<FoodRoutineObjectData>>

}