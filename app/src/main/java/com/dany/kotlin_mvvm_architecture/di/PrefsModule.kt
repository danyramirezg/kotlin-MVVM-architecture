package com.dany.kotlin_mvvm_architecture.di

import android.app.Application
import com.dany.kotlin_mvvm_architecture.Util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferencesHelper{
        return SharedPreferencesHelper(app)

    }
}