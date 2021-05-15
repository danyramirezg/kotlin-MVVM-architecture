package com.dany.kotlin_mvvm_architecture.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dany.kotlin_mvvm_architecture.R
import com.dany.kotlin_mvvm_architecture.model.Animal
import com.dany.kotlin_mvvm_architecture.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

	private lateinit var viewModel: ListViewModel
	private val listAdapter = AnimalListAdapter(arrayListOf())

	//Creating the Observer Animal
	private val animalListDataObserver = Observer<List<Animal>> { list ->
		list?.let {
			animalList.visibility = View.VISIBLE
			listAdapter.updateAnimalList(it)
		}
	}

	private val loadingLiveDataObserver=  Observer<Boolean> { isLoading ->
		loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
		if (isLoading) {
			listError.visibility = View.GONE
			animalList.visibility = View.GONE
		}

	}

	private val errorLiveDataObserver = Observer<Boolean> { isError ->
		listError.visibility = if (isError) View.VISIBLE else View.GONE
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
		viewModel.animals.observe(viewLifecycleOwner, animalListDataObserver)
		viewModel.loading.observe(viewLifecycleOwner, loadingLiveDataObserver)
		viewModel.loadError.observe(viewLifecycleOwner, errorLiveDataObserver)
		viewModel.refresh()

		animalList.apply {
			// Creates a GRID for the Recycler view with 2 columns
			layoutManager = GridLayoutManager(context, 2)
			adapter = listAdapter
		}
	}
}