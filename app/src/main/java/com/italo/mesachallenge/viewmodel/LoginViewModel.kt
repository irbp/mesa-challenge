package com.italo.mesachallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.italo.domain.base.Failure
import com.italo.domain.base.UserParams
import com.italo.domain.model.User
import com.italo.domain.usecases.SaveCurrentUserUseCase

class LoginViewModel(private val useCase: SaveCurrentUserUseCase) : BaseViewModel() {

    private val _isSuccess = MutableLiveData<Boolean>(false)
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun saveCurrentUser(name: String, picUrl: String, onError: (Failure) -> Unit) {
        val user = User(name, picUrl)
        useCase.execute(UserParams(user)) {
            it.oneOf({ error -> onError(error) }, ::handleCurrentUser)
        }
    }

    fun checkLogin() {
        val accessToken = AccessToken.getCurrentAccessToken()
        _isSuccess.value = accessToken != null
    }

    private fun handleCurrentUser(success: Boolean) {
        _isSuccess.value = success
    }
}