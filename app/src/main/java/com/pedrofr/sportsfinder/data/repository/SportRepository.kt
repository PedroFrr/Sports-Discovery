package com.pedrofr.sportsfinder.data.repository

import com.pedrofr.sportsfinder.data.model.User
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.flow.Flow

interface SportRepository {
    suspend fun getSports() : Flow<Result<List<*>>>

    suspend fun getEvents(sportKey: String) :  Flow<Result<List<*>>>

    suspend fun getUserDetail(userId: String): User
}