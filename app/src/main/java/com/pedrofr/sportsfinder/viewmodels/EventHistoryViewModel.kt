package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.model.BetWithEvents
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Result
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

class EventHistoryViewModel(private val repository: SportRepository) : ViewModel(), KoinComponent {

    private val sharedPrefs by inject<SharedPrefManager>()
    private val userId = sharedPrefs.getLoggedInUserId()

    val result = repository.getNonPendingBets(userId).asLiveData()

    private val _saveLiveData = MutableLiveData<Boolean>()
    fun getSaveLiveData() = _saveLiveData

    fun settleBet(betWithEvents: BetWithEvents, isWon: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if(canSettleBet(betWithEvents)){
                val bet = betWithEvents.bet
                val currentBalance = repository.getUserBalance(userId)
                val totalWon = bet.stake.times(bet.totalOdd)

                val newBalance = if(isWon){currentBalance.plus(totalWon)}else{currentBalance.minus(totalWon)}

                repository.settleBet(bet.betId, isWon)
                repository.updateUserBalance(userId, newBalance)
                _saveLiveData.postValue(true)
            }else{
                _saveLiveData.postValue(false)
            }

        }

    }

    private fun canSettleBet(betWithEvents: BetWithEvents): Boolean{
        val twoHoursTime = TimeUnit.HOURS.toMillis(2)
        //TODO add unit test
        val currentTime = System.currentTimeMillis()
        return betWithEvents.events.first().startTime.plus(twoHoursTime) >= currentTime
        }
}