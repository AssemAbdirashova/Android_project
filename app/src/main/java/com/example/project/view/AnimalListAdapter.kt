package com.example.project.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.databinding.ItemAnimalBinding
import com.example.project.model.Animal

class AnimalListAdapter(private val animalList: ArrayList<Animal>): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(inflater, R.layout.item_animal, parent, false)

            //inflater.inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    fun updateAnimalList(newAnimalList: List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalListAdapter.AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.listener = this

//        holder.view.animalLayout.setOnClickListener{
//            val action = ListFragmentDirections.listDetail(animalList[position])
//            Navigation.findNavController(holder.view).navigate(action)
//        }
    }
    class AnimalViewHolder(var view: ItemAnimalBinding): RecyclerView.ViewHolder(view.root) {

    }

    override fun onClick(v: View) {
        for(animal in animalList)
            if(v.tag == animal.name){
            val action = ListFragmentDirections.listDetail(animal)
            Navigation.findNavController(v).navigate(action)
        }
    }

}