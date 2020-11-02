package com.pedrofr.sportsfinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Bet")
data class Bet(
    @PrimaryKey val betId: String = UUID.randomUUID().toString(),
    val userCreatorId: String,
    var stake: Double = 0.0,
    //TODO rethink the data model
    val selectedTeam: String,
    val totalOdd: Double = 0.0,
    val isSettled: Boolean = false,
    val isPending: Boolean = true //If it's pending it shouldn't appear on the Betting History but only on the Bottom Sheet
)