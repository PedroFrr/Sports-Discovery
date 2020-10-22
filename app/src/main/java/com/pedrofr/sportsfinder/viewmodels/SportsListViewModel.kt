package com.pedrofr.sportsfinder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.data.repository.SportRepository

class SportsListViewModel(private val repository : SportRepository): ViewModel() {

    val result: LiveData<List<Sport>> = liveData {
        emit (repository.getSports())
    }


}