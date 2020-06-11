   package com.example.project.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.project.R
import com.example.project.databinding.FragmentDetailBinding
import com.example.project.model.Animal
import com.example.project.model.AnimalPalette

class DetailFragment : Fragment() {

    var animal: Animal? = null
    private lateinit var dataBinding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // return inflater.inflate(R.layout.fragment_detail, container, false)
       dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            animal = DetailFragmentArgs.fromBundle(it!!).animal
        }
//        context?.let {
//            animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
//        }
        dataBinding.animal = animal
//        animalName.text = animal?.name
//        animallocation.text = animal?.location
//        lifespan.text = animal?.lifeSpan
//        animalDiet.text = animal?.diet
        animal?.imageUrl?.let {
            setupBackgroundColor(it)
        }
    }
    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate() { palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            //animalLayout.setBackgroundColor(intColor)
                            dataBinding.palette = AnimalPalette(intColor)
                        }
                }
            }
            )
    }
}