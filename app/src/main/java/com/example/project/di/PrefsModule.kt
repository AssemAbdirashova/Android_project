package com.example.project.di

import android.app.Application
import com.example.project.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule{

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferencesHelper{
        return SharedPreferencesHelper(app)
    }
}