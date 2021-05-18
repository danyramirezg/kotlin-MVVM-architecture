package com.dany.kotlin_mvvm_architecture.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dany.kotlin_mvvm_architecture.R
import com.dany.kotlin_mvvm_architecture.model.Animal
import com.dany.kotlin_mvvm_architecture.util.getProgressDrawable
import com.dany.kotlin_mvvm_architecture.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.*


class DetailFragment : Fragment() {

	var animal: Animal? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_detail, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		arguments?.let {
			animal = DetailFragmentArgs.fromBundle(it).animal
		}

		context?.let {
			animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
		}

		animalName.text = animal?.name
		animalLocation.text = animal?.location
		animalLifespan.text  = animal?.lifeSpan
		animalDiet.text = animal?.diet

		animal?.imageUrl?.let { imageUrl ->
			setUpBackgroundColor(imageUrl)
		}
	}

	private fun setUpBackgroundColor(imageUrl: String) {

		Glide.with(this)
			.asBitmap()
			.load(imageUrl)
			.into(object : CustomTarget<Bitmap>() {

				override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
					// Palette is a library Android that choose a color base on an Image and its dominant colors
					// More info about Palette: https://developer.android.com/training/material/palette-colors
					// -> resource is the bitmap from the image we load before
					Palette.from(resource).generate() { palette ->
							val intColor = palette?.lightMutedSwatch?.rgb ?: 0
							animalLayout.setBackgroundColor(intColor)
						}
				}

				override fun onLoadCleared(placeholder: Drawable?) {
				}
			})
	}
}