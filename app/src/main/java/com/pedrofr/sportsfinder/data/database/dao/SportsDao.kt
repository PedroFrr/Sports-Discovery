package com.pedrofr.sportsfinder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pedrofr.sportsfinder.data.model.Odd
import com.pedrofr.sportsfinder.data.model.Sport

@Dao
interface SportsDao {

    @Query("SELECT * FROM Sport Order By title ")
    fun getSports(): List<Sport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSports(sports: List<Sport>)

    @Query("SELECT * FROM Odd WHERE sportsKey = :sportsId")
    fun getOdds(sportsId: String): List<Odd>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOdds(odds: List<Odd>)

}