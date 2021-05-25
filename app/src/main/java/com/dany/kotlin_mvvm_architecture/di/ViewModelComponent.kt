package com.dany.kotlin_mvvm_architecture.di

import com.dany.kotlin_mvvm_architecture.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, PrefsModule::class, AppModule::class])
interface ViewModelComponent {

    fun inject(viewModel: ListViewModel)

}