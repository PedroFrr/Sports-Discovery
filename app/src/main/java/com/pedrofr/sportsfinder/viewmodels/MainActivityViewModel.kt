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
            if(canSaveBet(bets)){
                val currentBalance = repository.getUserBalance(userId)
                val betsTotalAmount = bets
                    .map { it.stake }
                    .sumByDouble { it }
                val newBalance = currentBalance.minus(betsTotalAmount)
                bets.forEach { bet ->
                    repository.updatePendingBet(userId = userId, betId = bet.betId, newStake = bet.stake)
                }
                //TODO add validation -> Total amount <= User Current Balance
                repository.updateUserBalance(userId, newBalance)
                _saveLiveData.postValue(true)
            }else{
                _saveLiveData.postValue(false)
            }

        }

    }

    fun removePendingBet(pendingBet: Bet){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBet(pendingBet)
        }
    }

    fun logout(){
        sharedPrefs.clearLoggedInUserId()
    }

    private fun canSaveBet(bets:List<Bet>): Boolean{
        //Validates if user has enough balance to make bets
        val currentBalance = repository.getUserBalance(userId)
        return bets.sumByDouble { it.stake } <= currentBalance
    }
}