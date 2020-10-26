package com.pedrofr.sportsfinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Event")
data class Event(
    @PrimaryKey val eventId: String = UUID.randomUUID().toString(),
    val sportsKey: String,
    val startTime: Long,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamOdd: Double = 0.0,
    val awayTeamOdd: Double = 0.0,
    val drawOdd: Double? = 0.0
)