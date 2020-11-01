package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
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
