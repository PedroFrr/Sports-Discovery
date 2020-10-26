package com.pedrofr.sportsfinder.data.database.dao

import androidx.room.*
import com.pedrofr.sportsfinder.data.model.Event
import com.pedrofr.sportsfinder.data.model.Sport
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
    fun addSports(sports: List<Sport>)

    /*
    Returns the list of events for the specified Sport
     */
    @Query("SELECT * FROM Event WHERE sportsKey = :sportsId Order By startTime")
    fun getEvents(sportsId: String): List<Event>

    /*
    Inserts a list of events to the DB
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvents(events: List<Event>)

    /*
        Returns all the bets made by the specified User
         */
    @Transaction
    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserWithBets(userId: String): List<UserWithBets>

}