package com.example.project.model

import com.example.project.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class AnimalApiService {

    @Inject
    lateinit var  api: AnimalApi
    init{
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<Apikey> {
        return api.getApiKey()
    }

    fun getAnimals(key: String): Single<List<Animal>> {
        return api.getAnimals(key)
    }
}