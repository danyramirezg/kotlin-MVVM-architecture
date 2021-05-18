package com.dany.kotlin_mvvm_architecture.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dany.kotlin_mvvm_architecture.R
import com.dany.kotlin_mvvm_architecture.databinding.ItemAnimalBinding
import com.dany.kotlin_mvvm_architecture.model.Animal
import com.dany.kotlin_mvvm_architecture.util.getProgressDrawable
import com.dany.kotlin_mvvm_architecture.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>):
	RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener{

	fun updateAnimalList(newAnimalList: List<Animal>) {
		animalList.clear()
		animalList.addAll(newAnimalList)
		notifyDataSetChanged()
	}

	class AnimalViewHolder(val view: ItemAnimalBinding): RecyclerView.ViewHolder(view.root)


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = DataBindingUtil.inflate<ItemAnimalBinding>(inflater, R.layout.item_animal, parent, false)
		return AnimalViewHolder(view)
	}

	override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {

		holder.view.animal = animalList[position]
		// To use key word this, implement the AnimalClickListener interface we created
		holder.view.listener = this
	}

	// This function comes from the interface AnimalClickListener
	override fun onClickAnimalListener(v: View) {

		// v.tag is set in the item_animal.xml,
		// which represents our unique identifier for each animal
		for (animal in animalList) {
			if (v.tag == animal.name){
				val action = ListFragmentDirections.actionDetail(animal)
				Navigation.findNavController(v).navigate(action)
			}
		}
	}

	override fun getItemCount(): Int {
		return animalList.size
	}
}