package com.pedrofr.sportsfinder.data.repository

import com.pedrofr.sportsfinder.data.model.Odd
import com.pedrofr.sportsfinder.data.model.Sport
import kotlinx.coroutines.flow.Flow

interface SportRepository {
    suspend fun  getSports() : List<Sport>

    suspend fun getOdds(sportKey: String) : Flow<List<Odd>>

}