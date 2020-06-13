package com.example.project.di

import com.example.project.viewmodel.ListVewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, AppModule::class, PrefsModule::class])
interface ViewModelComponent {

    fun inject(viewModel: ListVewModel)

}