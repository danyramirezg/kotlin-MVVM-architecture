package com.dany.kotlin_mvvm_architecture.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dany.kotlin_mvvm_architecture.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}