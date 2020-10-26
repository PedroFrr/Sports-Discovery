package com.pedrofr.sportsfinder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pedrofr.sportsfinder.data.model.Event
import com.pedrofr.sportsfinder.data.model.Sport

@Dao
interface SportsDao {

    @Query("SELECT * FROM Sport Order By title ")
    fun getSports(): List<Sport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSports(sports: List<Sport>)

    @Query("SELECT * FROM Odd WHERE sportsKey = :sportsId Order By startTime")
    fun getEvents(sportsId: String): List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvents(events: List<Event>)

}