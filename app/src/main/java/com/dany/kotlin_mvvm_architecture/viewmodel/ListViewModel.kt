package com.dany.kotlin_mvvm_architecture.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dany.kotlin_mvvm_architecture.di.*
import com.dany.kotlin_mvvm_architecture.model.Animal
import com.dany.kotlin_mvvm_architecture.model.AnimalApiService
import com.dany.kotlin_mvvm_architecture.model.ApiKey
import com.dany.kotlin_mvvm_architecture.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(application: Application): AndroidViewModel(application) {

	constructor(application: Application, test: Boolean = true): this(application) {
		injected = true
	}


	// Creating Mutable Live data Variables
	val animals by lazy { MutableLiveData<List<Animal>>() }
	val loadError by lazy { MutableLiveData<Boolean>()}
	val loading by lazy { MutableLiveData<Boolean>()}

	private val disposable = CompositeDisposable()

	//AnimalApiService() contains the retrofit setUp
	@Inject
	lateinit var apiService : AnimalApiService

	@Inject
	//@field:TypeOfContext(CONTEXT_APP) <- I think it is not
	// necessary to use "field:" anymore
	@TypeOfContext(CONTEXT_APP)
	lateinit var prefs: SharedPreferencesHelper

	private var invalidApiKey = false
	private var injected = false

	fun inject() {
		// inject only if it is false,
		// it means inject Only if this is NOT a test
		if (!injected) {

			// DaggerViewModelComponent.create().inject(this) -> doesn't work
			// anymore because appModule needs an application
			// and appModule is a injection Module in the viewModelComponent
			// Instead we use builder()

			DaggerViewModelComponent.builder()
				.appModule(AppModule(getApplication()))
				.build()
				.inject(this)
		}
	}

	// Store the key into the sharedPreferences File, so
	// we just ask for the key once
	//This refresh function gets call when doing scrolling
	// in the screen, so new list of animals will appear, but
	// the apiKey will be the same everytime we ask for that
	// new list of animals
	fun refresh() {
		inject()
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
		inject()
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