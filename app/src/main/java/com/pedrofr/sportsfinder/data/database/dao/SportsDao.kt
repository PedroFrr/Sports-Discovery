package com.pedrofr.sportsfinder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pedrofr.sportsfinder.data.model.Sport

@Dao
interface SportsDao {

    @Query("SELECT * FROM Sport")
    fun getSports(): List<Sport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSports(sports: List<Sport>)

}