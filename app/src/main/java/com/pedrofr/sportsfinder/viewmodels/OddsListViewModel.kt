package com.pedrofr.sportsfinder.viewmodels

import android.accounts.NetworkErrorException
import androidx.lifecycle.*
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OddsListViewModel(private val repository: SportRepository): ViewModel() {

    var result:LiveData<Result<Any>> = MutableLiveData()

    //TODO if this works see what are the advantages of returning Flow. Search about multiple values...
    fun fetchEvents(sportKey: String){
        viewModelScope.launch {
            try {
                result = repository.getEvents(sportKey)
                    .asLiveData(viewModelScope.coroutineContext + Dispatchers.Default)
            }catch (e: NetworkErrorException){
                //TODO handle network error
            }
        }
    }


}