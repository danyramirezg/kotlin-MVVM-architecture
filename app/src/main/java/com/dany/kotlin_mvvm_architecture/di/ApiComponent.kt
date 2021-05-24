package com.dany.kotlin_mvvm_architecture.di

import com.dany.kotlin_mvvm_architecture.model.AnimalApiService
import dagger.Component

// A component is a class that makes the connection between a module and the class that I want
// that module to inject into

@Component(modules = [ApiModule::class]) // modules needs to be an array of module classes
interface ApiComponent {

    fun inject(service: AnimalApiService) // It tells the system where the functionality will be injected
                                        // In this case I'll inject everything into AnimalApiService
}