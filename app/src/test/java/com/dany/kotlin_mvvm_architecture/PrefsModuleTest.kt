package com.dany.kotlin_mvvm_architecture

import android.app.Application
import com.dany.kotlin_mvvm_architecture.Util.SharedPreferencesHelper
import com.dany.kotlin_mvvm_architecture.di.PrefsModule

class PrefsModuleTest(val mockPrefs: SharedPreferencesHelper) : PrefsModule() {
    override fun provideSharedPreferences(app: Application): SharedPreferencesHelper {
        return mockPrefs
    }
}