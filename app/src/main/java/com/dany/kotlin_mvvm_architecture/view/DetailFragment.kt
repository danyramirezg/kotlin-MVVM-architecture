package com.dany.kotlin_mvvm_architecture.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dany.kotlin_mvvm_architecture.R
import com.dany.kotlin_mvvm_architecture.databinding.FragmentDetailBinding
import com.dany.kotlin_mvvm_architecture.model.Animal
import com.dany.kotlin_mvvm_architecture.model.AnimalPalette


class DetailFragment : Fragment() {

	var animal: Animal? = null
	var backgroundColor: Int? = null
	private  lateinit var dataBinding: FragmentDetailBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
		return dataBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		arguments?.let {
			animal = DetailFragmentArgs.fromBundle(it).animal
		}

		// remove this code because the dataBinding now handles the load image
		//context?.let {
		//	dataBinding.animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
		//}

		animal?.imageUrl?.let { imageUrl ->
			setUpBackgroundColor(imageUrl)
		}
		// Pass the animal object to dataBinging for the view
		dataBinding.animal = animal
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
					Palette.from(resource).generate { palette ->
							val intColor = palette?.lightMutedSwatch?.rgb ?: 0

							// Pass the AnimalPalette object to dataBinging for the view
							dataBinding.palette = AnimalPalette(intColor)
						}
				}

				override fun onLoadCleared(placeholder: Drawable?) {
				}
			})
	}
}