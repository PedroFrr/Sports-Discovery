package com.pedrofr.sportsfinder.data.repository

import com.pedrofr.sportsfinder.data.model.*
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.flow.Flow

interface SportRepository {

    suspend fun getEvents(sportKey: String) :  Flow<Result<List<*>>>

    suspend fun getUserDetail(userId: String): User?

    suspend fun getNumberOfUserBets(userId: String): Int

    suspend fun createUser(user: User)

    fun getUserDetailByUsername(username: String): User?

    suspend fun fetchSportsByQuery(query: String): Flow<Result<List<*>>>

    fun fetchPendingBets(userId: String): Flow<List<BetWithEvents>>

    suspend fun createPendingBet(bet: Bet)

    suspend fun createBetWithEvent(betWithEvents: BetWithEventCrossRef)

    suspend fun updatePendingBet(userId: String, betId: String, newStake: Double)

    suspend fun deleteBet(bet: Bet)

    suspend fun updateUserBalance(userId: String, newBalance: Double)

    fun getUserBalance(userId: String): Double

    suspend fun getNonPendingBets(userId: String): Flow<Result<List<*>>>
}