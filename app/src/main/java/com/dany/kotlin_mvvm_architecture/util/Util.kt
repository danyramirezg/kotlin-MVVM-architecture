package com.dany.kotlin_mvvm_architecture.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dany.kotlin_mvvm_architecture.R

//Spinner to display while loading
fun getProgressDrawable(context: Context): CircularProgressDrawable {
	return CircularProgressDrawable(context).apply {
		// Size of the spinner
		strokeWidth = 10f
		centerRadius = 50f
		start()
	}

}

//Creating a loadImage function from ImageView
fun ImageView.loadImage(uri: String?, progressDrawable:  CircularProgressDrawable) {
	val options = RequestOptions()
		.placeholder(progressDrawable)
		.error(R.mipmap.ic_launcher_round)
	Glide.with(context)
		.setDefaultRequestOptions(options)
		.load(uri)
		.into(this)

}