package com.italo.mesachallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.italo.domain.base.Failure

open class BaseViewModel : ViewModel() {

    var error: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleError(error: Failure) {
        this.error.value = error
    }

}