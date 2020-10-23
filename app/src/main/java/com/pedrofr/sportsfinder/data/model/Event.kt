package com.pedrofr.sportsfinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Odd")
data class Odd(
    @PrimaryKey(autoGenerate = true) val oddsKey: Long = 0,
    val sportsKey: String,
    val startTime: Long,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamOdd: Double = 0.0,
    val awayTeamOdd: Double = 0.0,
    val drawOdd: Double? = 0.0
)