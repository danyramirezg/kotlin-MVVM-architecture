package com.dany.kotlin_mvvm_architecture.di

import com.dany.kotlin_mvvm_architecture.model.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

	fun inject(service: AnimalApiService)
}