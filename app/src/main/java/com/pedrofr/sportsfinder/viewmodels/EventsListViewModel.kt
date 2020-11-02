package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.model.BetWithEventCrossRef
import com.pedrofr.sportsfinder.data.model.BetWithEvents
import com.pedrofr.sportsfinder.data.model.Event
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Result
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class EventsListViewModel(private val repository: SportRepository, private val sharedPrefs: SharedPrefManager): ViewModel() {

    private var debouncePeriod: Long = 500
    private var searchJob: Job? = null

    val result = MutableLiveData<Result<Any>>()
    private val userId = sharedPrefs.getLoggedInUserId()

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

    fun setPendingBet(eventId: String, selectedOdd: Double, selectedTeam: String){
        viewModelScope.launch(Dispatchers.IO) {
            val betId = UUID.randomUUID().toString()
            val bet = Bet(betId = betId, userCreatorId = userId, selectedTeam = selectedTeam, totalOdd = selectedOdd)
            repository.createPendingBet(bet)
            val betWithEventCrossRef = BetWithEventCrossRef(betId = betId, eventId = eventId)
            repository.createBetWithEvent(betWithEventCrossRef)
        }
    }

}