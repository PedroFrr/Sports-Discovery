package com.pedrofr.animaldiscovery.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.pedrofr.animaldiscovery.data.model.Sport

@Dao
interface SportsDao {

    @Query("SELECT * FROM Sport")
    fun getSports(): List<Sport>

}