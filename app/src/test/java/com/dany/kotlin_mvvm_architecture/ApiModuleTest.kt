package com.dany.kotlin_mvvm_architecture

import com.dany.kotlin_mvvm_architecture.di.ApiModule
import com.dany.kotlin_mvvm_architecture.model.AnimalApiService

class ApiModuleTest(private val mockService: AnimalApiService): ApiModule() {
	override fun provideAnimalApiService(): AnimalApiService {
		return mockService
	}
}