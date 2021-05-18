package com.dany.kotlin_mvvm_architecture.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

	// ApiKey is a dataClass
	@GET("getKey")
	fun getApiKeyEndPoint(): Single<ApiKey>

	// key is necessary to do a POST {key: valueKey}
	@FormUrlEncoded
	@POST("getAnimals")
	fun getAnimalsEndPoint(@Field("key") valueKey: String): Single<List<Animal>>
}