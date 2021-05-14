package com.dany.kotlin_mvvm_architecture.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dany.kotlin_mvvm_architecture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	lateinit var binding: ActivityMainBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}

}