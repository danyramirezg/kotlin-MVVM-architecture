package com.dany.kotlin_mvvm_architecture.di

import android.app.Application
import com.dany.kotlin_mvvm_architecture.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

	@Singleton
	@Provides
	fun providerSharedPreferences(app: Application): SharedPreferencesHelper {
		return SharedPreferencesHelper(app)
	}
}