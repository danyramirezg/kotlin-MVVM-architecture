package com.dany.kotlin_mvvm_architecture.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    // Single is an observable that can be observed by another entity
    // Single returns a single value and then finished
    @GET("getKey")
    fun getApiKey(): Single<ApiKey>

    @FormUrlEncoded // Allow us to use the @Field parameters (used with form encoding)
    @POST("getAnimals")
    // This has to take a key parameter
    fun getAnimals(@Field("key") key: String): Single<List<Animal>>
}