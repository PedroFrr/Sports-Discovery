package com.pedrofr.sportsfinder.data.database.dao

import androidx.room.*
import com.pedrofr.sportsfinder.data.model.Event
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.data.model.User
import com.pedrofr.sportsfinder.data.model.UserWithBets

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
    fun updateUserBalance(userId: String, newBalance: Long)

}