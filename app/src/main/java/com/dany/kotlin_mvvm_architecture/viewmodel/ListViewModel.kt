package com.dany.kotlin_mvvm_architecture.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dany.kotlin_mvvm_architecture.model.Animal

// This class is going make the connection between the model and the view
class ListViewModel(application: Application) : AndroidViewModel(application) {

    // Lazy means that the system is not going to instantiate this live-data variable unless and,
    // when it is needed. If it never is used in the code, it is not created
    // LiveData: Is an observable that provides different values for whoever is listening
    // Mutable: can change and add new values to the list whenever I want
    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    fun refresh() {
        getAnimals()
    }

    // Function to retrieve the information from the backend:
    private fun getAnimals() {
        val a1 = Animal("dog")
        val a2 = Animal("cat")
        val a3 = Animal("alligator")
        val a4 = Animal("bee")
        val a5 = Animal("elephant")
        val a6 = Animal("flamingo")

        val animalList = arrayListOf(a1, a2, a3, a4, a5, a6)

        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}