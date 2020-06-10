package com.example.project.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.model.Animal
import com.example.project.util.getProgressDrawable
import com.example.project.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    fun updateAnimalList(newAnimalList: List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalListAdapter.AnimalViewHolder, position: Int) {
        holder.view.animalName.text = animalList[position].name
        holder.view.animalImage.loadImage(animalList[position].imageUrl,
        getProgressDrawable(holder.view.context))
    }
    class AnimalViewHolder(var view: View): RecyclerView.ViewHolder(view) {

    }

}