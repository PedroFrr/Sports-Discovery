package com.pedrofr.sportsfinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "Sport")
data class Sport(
    @PrimaryKey val sportsKey: String = UUID.randomUUID().toString(),
    val title: String,
    val imageUrl: String = ""
)