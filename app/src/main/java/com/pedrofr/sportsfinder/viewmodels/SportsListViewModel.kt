package com.pedrofr.sportsfinder.viewmodels

import android.accounts.NetworkErrorException
import androidx.lifecycle.*
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Failure
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SportsListViewModel(private val repository : SportRepository): ViewModel() {

    var result:LiveData<Result<Any>> = MutableLiveData()

    fun fetchSports(){
        viewModelScope.launch {
            try {
                result = repository.getSports()
                    .asLiveData(viewModelScope.coroutineContext + Dispatchers.Default)
            }catch (error: NetworkErrorException){
                Failure(error)
            }
        }
    }


}