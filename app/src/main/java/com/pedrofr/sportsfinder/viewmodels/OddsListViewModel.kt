package com.pedrofr.sportsfinder.viewmodels

import android.accounts.NetworkErrorException
import androidx.lifecycle.*
import com.pedrofr.sportsfinder.data.model.CompositeItem
import com.pedrofr.sportsfinder.data.model.Odd
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class OddsListViewModel(private val repository: SportRepository): ViewModel() {

    var result:LiveData<Result<Any>> = MutableLiveData()

    //TODO if this works see what are the advantages of returning Flow. Search about multiple values...
    fun fetchOdds(sportKey: String){
        viewModelScope.launch {
            try {
                //TODO section Header with composite items
                val odds = repository.getOdds(sportKey)
                val composites = mutableListOf<CompositeItem>()


                result = repository.getOdds(sportKey)
                    .asLiveData(viewModelScope.coroutineContext + Dispatchers.Default)
            }catch (e: NetworkErrorException){
                //TODO handle network error
            }
        }
    }


}