package com.dany.kotlin_mvvm_architecture.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.dany.kotlin_mvvm_architecture.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
open class PrefsModule {

	@Singleton
	@Provides
	@TypeOfContext(CONTEXT_APP)
	open fun providerSharedPreferences(app: Application): SharedPreferencesHelper {
		return SharedPreferencesHelper(app)
	}

	@Singleton
	@Provides
	@TypeOfContext(CONTEXT_ACTIVITY)
	fun provideActivitySharedPreferences(activity: AppCompatActivity): SharedPreferencesHelper {
		return SharedPreferencesHelper(activity)
	}
}


const val CONTEXT_APP ="Application context"
const val CONTEXT_ACTIVITY = "Activity context"

@Qualifier
annotation class TypeOfContext(val type: String)