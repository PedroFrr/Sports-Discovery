package com.pedrofr.sportsfinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Bet")
data class Bet(
    @PrimaryKey val betId: String = UUID.randomUUID().toString(),
    val userCreatorId: String,
    val wageredAmount: Double,
    val totalOdd: Double,
    val isSettled: Boolean = false
)