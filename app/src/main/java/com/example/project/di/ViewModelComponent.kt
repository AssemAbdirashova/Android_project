package com.example.project.di

import com.example.project.viewmodel.ListVewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListVewModel)

}