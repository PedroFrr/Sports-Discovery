package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager

class LoginViewModel(
    private val sharedPrefs: SharedPrefManager
): ViewModel() {

    private val loginState = MutableLiveData<LoginViewState>()

    fun getLoginState(): LiveData<LoginViewState> = loginState

    fun loginUser(userId: String, username: String, password: String){
        if(username.length >= 4 && password.length >= 4){
            saveUserId(userId)
        }else{
            loginState.value = LoginViewState.InvalidCredentials
        }
    }

    private fun saveUserId(userId: String){
        sharedPrefs.setLoggedInUserId(userId)
        loginState.value = LoginViewState.UserLoggedIn
    }


}

sealed class LoginViewState {
    object UserLoggedIn : LoginViewState()
    object InvalidCredentials : LoginViewState()
}