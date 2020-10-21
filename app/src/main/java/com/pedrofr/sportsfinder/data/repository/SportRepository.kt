package com.pedrofr.sportsfinder.data.repository

import com.pedrofr.sportsfinder.data.model.Odd
import com.pedrofr.sportsfinder.data.model.Sport

interface SportRepository {
    suspend fun getSports() : List<Sport>

    suspend fun getOdds(sportKey: String) : List<Odd>

}