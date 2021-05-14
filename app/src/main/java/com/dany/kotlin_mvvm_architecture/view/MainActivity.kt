package com.dany.kotlin_mvvm_architecture.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.dany.kotlin_mvvm_architecture.R
import com.dany.kotlin_mvvm_architecture.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

	private lateinit var navController: NavController

	lateinit var binding: ActivityMainBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		navController = Navigation.findNavController(this, R.id.fragment)
		NavigationUI.setupActionBarWithNavController(this, navController)

	}

	override fun onSupportNavigateUp(): Boolean {
		return NavigationUI.navigateUp(navController, null)

	}

}