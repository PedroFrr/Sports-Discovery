package com.pedrofr.animaldiscovery.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pedrofr.animaldiscovery.data.model.Sport

@Dao
interface SportsDao {
    @Query("SELECT * FROM Sport")
    suspend fun getAnimals() : List<Sport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAnimals(sports: List<Sport>)
}