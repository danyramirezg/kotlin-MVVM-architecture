package com.dany.kotlin_mvvm_architecture.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dany.kotlin_mvvm_architecture.R
import com.dany.kotlin_mvvm_architecture.model.Animal
import com.dany.kotlin_mvvm_architecture.util.getProgressDrawable
import com.dany.kotlin_mvvm_architecture.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>):
	RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(){

	fun updateAnimalList(newAnimalList: List<Animal>) {
		animalList.clear()
		animalList.addAll(newAnimalList)
		notifyDataSetChanged()
	}

	class AnimalViewHolder(view: View): RecyclerView.ViewHolder(view)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_animal, parent, false)
		return AnimalViewHolder(view)
	}

	override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
		holder.itemView.animalName.text = animalList[position].name
		holder.itemView.animalImage.loadImage(animalList[position].imageUrl, getProgressDrawable(holder.itemView.context))
		holder.itemView.animalLayout.setOnClickListener {
			val action = ListFragmentDirections.actionDetail(animalList[position])
			Navigation.findNavController(holder.itemView).navigate(action)
		}
	}

	override fun getItemCount(): Int {
		return animalList.size
	}
}