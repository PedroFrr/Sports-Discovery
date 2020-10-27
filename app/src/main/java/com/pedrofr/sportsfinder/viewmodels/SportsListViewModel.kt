package com.pedrofr.sportsfinder.viewmodels

import android.accounts.NetworkErrorException
import androidx.lifecycle.*
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Failure
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SportsListViewModel(private val repository: SportRepository) : ViewModel() {

    private var debouncePeriod: Long = 500
    private var searchJob: Job? = null

    val result = MutableLiveData<Result<Any>>()

    fun fetchSportsByTitle(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            repository.fetchSportsByQuery(query)
                .collect {
                    result.postValue(it)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

}
