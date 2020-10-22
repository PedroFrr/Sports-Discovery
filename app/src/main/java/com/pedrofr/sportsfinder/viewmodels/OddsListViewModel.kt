package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.sportsfinder.data.model.Odd
import com.pedrofr.sportsfinder.data.repository.SportRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OddsListViewModel(private val repository: SportRepository): ViewModel() {

    val oddsListLiveData = MutableLiveData<List<Odd>>()

    //TODO if this works see what are the advantages of returning Flow. Search about multiple values...

    fun saveOddsLiveData(sportKey: String){
        viewModelScope.launch {
            repository.getOdds(sportKey)
                .collect {
                    oddsListLiveData.postValue(it)
                }
        }
    }


}