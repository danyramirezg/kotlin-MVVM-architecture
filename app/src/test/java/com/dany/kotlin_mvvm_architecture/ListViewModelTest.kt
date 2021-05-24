package com.dany.kotlin_mvvm_architecture

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dany.kotlin_mvvm_architecture.di.AppModule
import com.dany.kotlin_mvvm_architecture.di.DaggerViewModelComponent
import com.dany.kotlin_mvvm_architecture.model.AnimalApiService
import com.dany.kotlin_mvvm_architecture.util.SharedPreferencesHelper
import com.dany.kotlin_mvvm_architecture.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
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