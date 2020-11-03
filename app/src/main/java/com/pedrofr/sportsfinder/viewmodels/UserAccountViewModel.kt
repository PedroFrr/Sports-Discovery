package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pedrofr.sportsfinder.data.model.User
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager

class UserAccountViewModel(
    private val repository: SportRepository,
    private val sharedPrefs: SharedPrefManager) : ViewModel(){

    val user: LiveData<User?> = liveData {
        val loggedInUserId = sharedPrefs.getLoggedInUserId()
        emit(repository.getUserDetail(loggedInUserId))
    }

    val numberBets = liveData {
        val userId = sharedPrefs.getLoggedInUserId()
        emit(repository.getNumberOfUserBets(userId))
    }


}