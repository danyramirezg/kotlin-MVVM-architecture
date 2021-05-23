@file:Suppress("DEPRECATION")

package com.dany.kotlin_mvvm_architecture.Util

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferencesHelper(context: Context) {

    private val PREF_API_KEY: String = "Api key"
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    // Stores the key call in the ListViewModel
    fun saveApiKey(key: String) {
        prefs.edit().putString(PREF_API_KEY, key).apply()
    }

    fun getApiKey() = prefs.getString(PREF_API_KEY, null) 

    }