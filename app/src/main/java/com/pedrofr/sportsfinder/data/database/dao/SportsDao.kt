package com.pedrofr.sportsfinder.data.database.dao

import androidx.room.*
import com.pedrofr.sportsfinder.data.model.*
import com.pedrofr.sportsfinder.networking.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface SportsDao {

    /*
    Returns the list of all Sports available on the App
     */
    @Query("SELECT * FROM Sport Order By title ")
    fun getSports(): List<Sport>

    /*
    Inserts a list of sports to the DB
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSports(sports: List<Sport>)

    /*
    Returns the list of events for the specified Sport. Ordered by ASC date
     */
    @Query("SELECT * FROM Event WHERE sportsKey = :sportsId ORDER BY startTime")
    fun getEvents(sportsId: String): List<Event>

    /*
    Returns the last event (last meaning last date)
     */
    @Query("SELECT * FROM Event WHERE sportsKey = :sportsId ORDER BY startTime DESC LIMIT 1 ")
    fun getLastEvent(sportsId: String): Event?

    /*
    Inserts a list of events to the DB
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<Event>)

    /*
        Returns all the bets made by the specified User
         */
    @Transaction
    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserWithBets(userId: String): List<UserWithBets>?

    /*
    Returns User Detail by id
     */
    @Query("SELECT * FROM User WHERE userId = :userId")
    suspend fun getUserDetailById(userId: String): User?

    /*
    Returns number of bets for a given User
     */
    @Query("SELECT COUNT(*) FROM Bet WHERE userCreatorId = :userId")
    suspend fun getNumberOfUserBets(userId: String): Int

    /*
    Creates a new User
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNewUser(user: User)

    /*
    Returns User Detail by Username
     */
    @Query("SELECT * FROM User WHERE username = :username")
    fun getUserDetailByUsername(username: String): User?

    /*
    Returns the list of Sports filtered by name
     */
    @Query("SELECT * FROM Sport WHERE title LIKE '%' || :sportTitle || '%' ")
    fun fetchSportsByTitle(sportTitle: String): List<Sport>

    /*
    Updates the user balance
     */
    @Query("UPDATE User SET balance = :newBalance WHERE userId = :userId")
    suspend fun updateUserBalance(userId: String, newBalance: Double)

    /*
    Retrives User Balance
     */
    @Query("SELECT balance FROM User WHERE userId = :userId")
    fun getUserBalance(userId: String): Double

    /*
    Creates a new bet record
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBet(bet: Bet)

    /*
    Retrieves non pending Bets
     */
    @Query("SELECT * FROM Bet WHERE isPending = 0 AND userCreatorId = :userId")
    fun getNonPendingBets(userId: String): List<BetWithEvents>

    /*
    Returns the list of events associated with a Bet
     */
    @Transaction
    @Query("SELECT * FROM Bet WHERE userCreatorId = :userId AND isPending = 1")
    fun getPendingBetsWithEvents(userId: String): Flow<List<BetWithEvents>>

    fun getPendingBetsUntilChanged(userId: String) = getPendingBetsWithEvents(userId).distinctUntilChanged()

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBetWithEvents(betWithEvents: BetWithEventCrossRef)

    /*
    Updates pending bet to a none pending
     */
    @Query("UPDATE Bet SET isPending = 0, stake = :newStake WHERE userCreatorId = :userId AND betId = :betId")
    suspend fun updateUserPendingBet(userId: String, betId: String, newStake: Double)

    @Delete
    suspend fun deleteBet(bet: Bet)


}