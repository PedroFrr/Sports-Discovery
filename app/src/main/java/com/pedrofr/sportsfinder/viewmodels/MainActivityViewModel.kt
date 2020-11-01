package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Loading
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import kotlinx.coroutines.flow.onStart
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivityViewModel(private val repository: SportRepository): ViewModel(), KoinComponent{

    private val sharedPrefs by inject<SharedPrefManager> ()
    private val userId = sharedPrefs.getLoggedInUserId()

    var result = repository.fetchPendingBets(userId).asLiveData()
}