package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Loading
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivityViewModel(private val repository: SportRepository) : ViewModel(), KoinComponent {

    private val sharedPrefs by inject<SharedPrefManager>()
    private val userId = sharedPrefs.getLoggedInUserId()
    private val _saveLiveData = MutableLiveData<Boolean>()

    var result = repository.fetchPendingBets(userId).asLiveData()

    fun getSaveLiveData() = _saveLiveData

    fun saveBet(bets: List<Bet>) {
        viewModelScope.launch(Dispatchers.IO) {
            val betIds = bets.map { it.betId }
            repository.updatePendingBet(userId = userId, betIds = betIds)
            _saveLiveData.postValue(true)
        }

    }

    fun removePendingBet(pendingBet: Bet){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBet(pendingBet)
        }
    }
}