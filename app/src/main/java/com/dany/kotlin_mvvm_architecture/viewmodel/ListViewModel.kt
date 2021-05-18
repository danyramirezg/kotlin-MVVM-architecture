package com.dany.kotlin_mvvm_architecture.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dany.kotlin_mvvm_architecture.model.Animal
import com.dany.kotlin_mvvm_architecture.model.AnimalApiService
import com.dany.kotlin_mvvm_architecture.model.ApiKey
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

	fun refresh() {
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
						e.printStackTrace()
						loading.value = false
						loadError.value = true
						animals.value = null
					}

				})
		)

	}

	override fun onCleared() {
		super.onCleared()
		disposable.clear()
	}
}