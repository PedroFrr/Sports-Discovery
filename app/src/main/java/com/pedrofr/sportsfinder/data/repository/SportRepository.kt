package com.pedrofr.sportsfinder.data.repository

import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.data.model.User
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.flow.Flow

interface SportRepository {

    suspend fun getEvents(sportKey: String) :  Flow<Result<List<*>>>

    suspend fun getUserDetail(userId: String): User?

    suspend fun getNumberOfUserBets(userId: String): Int

    suspend fun createUser(user: User)

    fun getUserDetailByUsername(username: String): User?

    suspend fun fetchSportsByQuery(query: String): Flow<Result<List<*>>>
}