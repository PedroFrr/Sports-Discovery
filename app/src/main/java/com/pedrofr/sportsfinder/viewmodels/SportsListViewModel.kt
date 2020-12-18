package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.LiveData
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

    private val _result = MutableLiveData<Result<Any>>()

    val getResultLiveData: LiveData<Result<Any>>
    get() = _result

    init {
        viewModelScope.launch {
            repository.getSports()
                .collect {
                    _result.postValue(it)
                }
        }
    }

    fun fetchSportsByTitle(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            if (query.length < 2) {
                repository.fetchSportsByQuery(query)
                    .collect {
                        _result.postValue(it)
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

}
