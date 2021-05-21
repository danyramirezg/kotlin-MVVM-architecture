package com.dany.kotlin_mvvm_architecture.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AnimalApiService {

    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)

        // Convert the Json I retrieved from the back-end to a list (from the data-classes),
        // Convert Json into objects:
        .addConverterFactory(GsonConverterFactory.create())

        // Convert the things that come from above into a singletons based on the type,
        // Convert the objects into observables
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnimalApi::class.java) // Pass the interface, I want to build the service on

    fun getApiKey(): Single<ApiKey> {
        return api.getApiKey()
    }

    fun getAnimals(key: String): Single<List<Animal>> {
        return api.getAnimals(key)
    }
}