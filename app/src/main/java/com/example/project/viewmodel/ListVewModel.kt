package com.example.project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project.di.AppModule
import com.example.project.di.DaggerViewModelComponent
import com.example.project.model.Animal
import com.example.project.model.AnimalApiService
import com.example.project.model.Apikey
import com.example.project.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListVewModel(application: Application): AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>()}
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>()}
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var   apiService: AnimalApiService

    @Inject
    lateinit var prefs: SharedPreferencesHelper

    private var invalidApikey = false

    init{
        DaggerViewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }

    fun refresh() {
        loading.value = true
        invalidApikey = false
        val key = prefs.getApiKey()
        if(key.isNullOrEmpty()){
            getKey()
        }else{
            getAnimals(key)
        }
        getKey()
    }

    fun hardRefresh() {
        loading.value = true
        getKey()
    }

    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Apikey>(){
                    override fun onSuccess(key: Apikey) {
                        if(key.key.isNullOrEmpty()){
                            loadError.value = true
                            loading.value = false
                        }
                        else{
                            prefs.saveApiKey(key.key)
                            getAnimals(key.key)

                        }
                    }

                    override fun onError(e: Throwable) {
                            if(!invalidApikey) {
                                invalidApikey = true
                                getKey()

                            }
                        else{
                                e.printStackTrace()
                                loading.value = false
                                animals.value = null
                                loadError.value = true
                            }

                    }

                }
                )
        )
    }
    private fun getAnimals(key: String){

        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>(){
                    override fun onSuccess(list: List<Animal>) {
                        loadError.value = false
                        animals.value = list
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        animals.value = null
                        loading.value = false
                        loadError.value = false
                    }

                })
        )

    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}