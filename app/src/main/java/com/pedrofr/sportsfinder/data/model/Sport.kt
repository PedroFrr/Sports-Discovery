package com.pedrofr.sportsfinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "Sport")
data class Sport(
    @PrimaryKey val sportId: String = UUID.randomUUID().toString(),
    val sports_key: String,
    val title: String
)