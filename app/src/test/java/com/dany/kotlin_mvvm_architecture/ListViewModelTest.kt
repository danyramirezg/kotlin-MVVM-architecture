package com.dany.kotlin_mvvm_architecture

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dany.kotlin_mvvm_architecture.di.AppModule
import com.dany.kotlin_mvvm_architecture.di.DaggerViewModelComponent
import com.dany.kotlin_mvvm_architecture.model.Animal
import com.dany.kotlin_mvvm_architecture.model.AnimalApiService
import com.dany.kotlin_mvvm_architecture.util.SharedPreferencesHelper
import com.dany.kotlin_mvvm_architecture.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ListViewModelTest {
	@get: Rule
	val rule = InstantTaskExecutorRule()

	@Mock
	lateinit var animalApiService: AnimalApiService

	@Mock
	lateinit var prefs: SharedPreferencesHelper

	private val key = "Test key"

	private val application: Application = Mockito.mock(Application::class.java)

	private var listViewModel = ListViewModel(application, true)

	@Before
	fun setUp(){
		MockitoAnnotations.initMocks(this)

		DaggerViewModelComponent.builder()
			.appModule(AppModule(application))
			.apiModule(ApiModuleTest(animalApiService))
			.prefsModule(PrefsModuleTest(prefs))
			.build()
			.inject(listViewModel)
	}

	// Creating the test
	fun getAnimalsSuccess() {
		Mockito.`when`(prefs.getApiKey()).thenReturn(key)
		val animal = Animal("cow", null, null, null, null, null, null)
		val animalList = listOf(animal)

		val testSingle = Single.just(animalList)

		Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)

		listViewModel.refresh()

		Assert.assertEquals(1, listViewModel.animals.value?.size)
		Assert.assertEquals(false, listViewModel.loadError.value)
		Assert.assertEquals(false, listViewModel.loading.value)
	}

	@Before
	fun setupRxSchedulers() {
		val immediate = object: Scheduler() {
			override fun createWorker(): Worker {
				return ExecutorScheduler.ExecutorWorker( { it.run() }, true)
			}

		}
		RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
		RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate}

	}
}