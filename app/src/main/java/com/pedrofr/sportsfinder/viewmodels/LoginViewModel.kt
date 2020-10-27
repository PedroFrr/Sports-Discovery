package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.*
import com.pedrofr.sportsfinder.data.model.User
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sharedPrefs: SharedPrefManager,
    private val repository: SportRepository
) : ViewModel() {

    private val _saveLiveData = MutableLiveData<Boolean>()
    fun getSaveLiveData(): LiveData<Boolean> = _saveLiveData

    private val loginState = MutableLiveData<LoginViewState>()

    fun getLoginState(): LiveData<LoginViewState> = loginState

    fun loginUser(username: String, password: String) {
        if (username.length >= 4 && password.length >= 4) {
            userLogin(username)
        } else {
            loginState.value = LoginViewState.InvalidCredentials
        }
    }

    //TODO improve
    private fun userLogin(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userDetail = repository.getUserDetailByUsername(username)
            if (userDetail == null) {
                repository.createUser(User(username = username, balance = 1000.0))
            }
            val userDetailAfter = repository.getUserDetailByUsername(username)
            sharedPrefs.setLoggedInUserId(userDetailAfter!!.userId )
        }

        loginState.value = LoginViewState.UserLoggedIn

    }

    fun checkIfUserIsLoggedIn() {

        if(sharedPrefs.getLoggedInUserId() != "") {
            loginState.value = LoginViewState.UserLoggedIn
        }
    }

}

sealed class LoginViewState {
    object UserLoggedIn : LoginViewState()
    object InvalidCredentials : LoginViewState()
}