package com.example.project.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.R
import com.example.project.model.Animal
import com.example.project.viewmodel.ListVewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private lateinit var viewModel: ListVewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())
    private val animalListDataObserver = Observer<List<Animal>> {
        list ->
        list?.let{
            animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }
    private val loadingLiveDateObserver = Observer<Boolean> {
        isLoading ->
        loadingView.visibility = if(isLoading) View.VISIBLE else View.GONE
        if(isLoading) {
            errorlist.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }
    private val errorLiveDateObserver = Observer<Boolean> {
        isError->
        errorlist.visibility = if(isError) View.VISIBLE else View.GONE
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListVewModel::class.java)
        viewModel.animals.observe(viewLifecycleOwner, animalListDataObserver)
        viewModel.loading.observe(viewLifecycleOwner, errorLiveDateObserver)
        viewModel.refresh()
        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }
        refreshLayout.setOnRefreshListener{
            animalList.visibility = View.GONE
            errorlist.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
            loadingView.visibility = View.GONE
        }

    }


}
