package com.dany.kotlin_mvvm_architecture.di

import com.dany.kotlin_mvvm_architecture.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewModel )

}