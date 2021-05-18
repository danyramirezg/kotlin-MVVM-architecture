package com.dany.kotlin_mvvm_architecture.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dany.kotlin_mvvm_architecture.model.Animal
import com.dany.kotlin_mvvm_architecture.model.AnimalApiService
import com.dany.kotlin_mvvm_architecture.model.ApiKey
import com.dany.kotlin_mvvm_architecture.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application): AndroidViewModel(application) {

	// Creating Mutable Live data Variables
	val animals by lazy { MutableLiveData<List<Animal>>() }
	val loadError by lazy { MutableLiveData<Boolean>()}
	val loading by lazy { MutableLiveData<Boolean>()}

	private val disposable = CompositeDisposable()
	//AnimalApiService() contains the retrofit setUp
	private val apiService = AnimalApiService()

	private val prefs = SharedPreferencesHelper(getApplication())

	private var invalidApiKey = false

	// Store the key into the sharedPreferences File, so
	// we just ask for the key once
	//This refresh function gets call when doing scrolling
	// in the screen, so new list of animals will appear, but
	// the apiKey will be the same everytime we ask for that
	// new list of animals
	fun refresh() {
		loading.value = true
		invalidApiKey = false

		val key = prefs.getApiKey()

		if (key.isNullOrEmpty()){
			getKey()
		} else {
			getAnimals(key)
		}
	}

	// Hard refresh will ask for a new Key when doing a refresh layout
	// basically refreshing the screen
	fun hardRefresh() {
		loading.value = true
		getKey()
	}


	private fun getKey() {
		disposable.add(
			apiService.getApiKey()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(object : DisposableSingleObserver<ApiKey>(){
					override fun onSuccess(apiKey: ApiKey) {
						// Check if the key is empty or NULL
						if (apiKey.key.isNullOrEmpty()) {
							loadError.value = true
							loading.value = false
						} else {
							//Stores the key into the shared preferences file
							prefs.saveApiKey(apiKey.key)
							getAnimals(apiKey.key)
						}
					}

					override fun onError(e: Throwable) {
						e.printStackTrace()
						loading.value = false
						loadError.value = true
					}

				})
		)
	}

	private fun getAnimals(key: String) {
		disposable.add(
			apiService.getAnimals(key)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(object : DisposableSingleObserver<List<Animal>>(){
					override fun onSuccess(listAnimals: List<Animal>) {
						loadError.value = false
						loading.value = false
						animals.value = listAnimals
					}

					override fun onError(e: Throwable) {
						if (!invalidApiKey) {
							invalidApiKey = true
							getKey()
						} else {
							e.printStackTrace()
							loading.value = false
							loadError.value = true
							animals.value = null
						}
					}

				})
		)

	}

	override fun onCleared() {
		super.onCleared()
		disposable.clear()
	}
}