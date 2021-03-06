package com.mocknetworking.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel<T> : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val status = MutableLiveData<Status>()
    val error = MutableLiveData<Throwable>()
    val data = MutableLiveData<T>()

    fun response(): MutableLiveData<T> {
        return data
    }

    fun status(): MutableLiveData<Status> {
        return status
    }

    fun error(): MutableLiveData<Throwable> {
        return error
    }

    override fun onCleared() {
        super.onCleared()
     compositeDisposable.dispose()
    }
}