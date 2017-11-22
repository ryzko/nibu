package com.ryzko.nibu.model.api

import com.ryzko.nibu.model.BabyResponse
import com.ryzko.nibu.model.TokenObject
import com.ryzko.nibu.model.User
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
                 @Field("grant_type") grantType:String):Observable<TokenObject>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username:String,
                 @Field("password") password:String,
                 @Field("client_id") clientID:Int,
                 @Field("client_secret") clientSecret:String,
                 @Field("grant_type") grantType:String):Observable<TokenObject>

    @Headers("Accept: application/json")
    @GET("user")
    fun user(@Header("Authorization") token:String):Observable<User>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("babies")
    fun addBaby(
            @Header("Authorization") token:String,
            @Field("username") username:String,
            @Field("birth_date") birth_date:String,
            @Field("avatar") avatar:String):Observable<BabyResponse>

    @Headers("Accept: application/json")
    @GET("babies")
    fun getAllBabies(
            @Header("Authorization") token:String):Observable<ArrayList<BabyResponse>>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("babies")
    fun updateBaby(
            @Header("Authorization") token:String,
            @Field("id") id:Int,
            @Field("username") username:String,
            @Field("birth_date") password:String,
            @Field("avatar") clientID:Int):Observable<BabyResponse>

}