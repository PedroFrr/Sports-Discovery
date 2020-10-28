package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: SportRepository): ViewModel() {

    val result = MutableLiveData<Result<Any>>()

    fun fetchPendingBets(userId: String){
        viewModelScope.launch {
            repository.fetchPendingBets(userId)
                .collect {
                    result.postValue(it)
                }
        }
    }
}