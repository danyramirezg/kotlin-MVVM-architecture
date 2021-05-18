@file:Suppress("DEPRECATION")

package com.dany.kotlin_mvvm_architecture.util

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferencesHelper(context: Context) {

	// getDefaultSharedPreferences -> uses a default preference-file name
	private val PREF_API_KEY = "Api key"
	private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

	//Stores the key call in the ListViewModel
	// apply will save the changes
	fun saveApiKey(key: String?) {
		prefs.edit().putString(PREF_API_KEY, key).apply()
	}

	fun getApiKey() = prefs.getString(PREF_API_KEY, null)

}