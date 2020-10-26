package com.pedrofr.sportsfinder.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["betId", "eventId"])
data class BetWithEventCrossRef(
    val betId: String,
    val eventId: String
)