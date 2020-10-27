package com.pedrofr.sportsfinder.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pedrofr.sportsfinder.data.model.User
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager

class UserViewModel(
    private val repository: SportRepository,
    private val sharedPrefs: SharedPrefManager) : ViewModel(){

    val result: LiveData<User> = liveData {
        val loggedInUserId = sharedPrefs.getLoggedInUserId()
        emit(repository.getUserDetail(loggedInUserId))
    }

    fun getUserDetail() = result

}