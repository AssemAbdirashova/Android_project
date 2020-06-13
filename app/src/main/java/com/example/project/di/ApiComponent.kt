package com.example.project.di

import com.example.project.model.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: AnimalApiService)

}