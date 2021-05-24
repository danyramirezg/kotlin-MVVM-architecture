package com.dany.kotlin_mvvm_architecture

import android.app.Application
import com.dany.kotlin_mvvm_architecture.di.PrefsModule
import com.dany.kotlin_mvvm_architecture.util.SharedPreferencesHelper

class PrefsModuleTest(val mockPrefs: SharedPreferencesHelper): PrefsModule()  {
	override fun providerSharedPreferences(app: Application): SharedPreferencesHelper {
		return mockPrefs
	}
}