package com.dany.kotlin_mvvm_architecture.di

import com.dany.kotlin_mvvm_architecture.model.AnimalApi
import com.dany.kotlin_mvvm_architecture.model.AnimalApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



@Module
class ApiModule {

    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"

    // I created the functionality here
    // Provides says which type of information will be provided
    @Provides
    fun provideAnimalApi(): AnimalApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)

            // Convert the Json I retrieved from the back-end to a list (from the data-classes),
            // Convert Json into objects:
            .addConverterFactory(GsonConverterFactory.create())

            // Convert the things that come from above into a singletons based on the type,
            // Convert the objects into observables
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AnimalApi::class.java) // Pass the interface, I want to build the service on
    }

    @Provides
    fun provideAnimalApiService(): AnimalApiService{
        return AnimalApiService()
    }

}