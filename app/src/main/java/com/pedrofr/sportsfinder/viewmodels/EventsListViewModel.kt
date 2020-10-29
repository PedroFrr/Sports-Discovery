package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Result
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EventsListViewModel(private val repository: SportRepository, private val sharedPrefs: SharedPrefManager): ViewModel() {

    private var debouncePeriod: Long = 500
    private var searchJob: Job? = null

    val result = MutableLiveData<Result<Any>>()

    fun fetchEvents(sportKey: String){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            repository.getEvents(sportKey)
                .collect {
                    result.postValue(it)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

    fun setPendingBet(totalOdds: Double){
        viewModelScope.launch {
            val userId = sharedPrefs.getLoggedInUserId()
            val bet = Bet(userCreatorId = userId, totalOdd = totalOdds)
            repository.createPendingBet(bet)
        }

    }

}