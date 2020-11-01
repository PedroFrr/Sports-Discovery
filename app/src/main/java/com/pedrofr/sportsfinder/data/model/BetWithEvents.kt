package com.pedrofr.sportsfinder.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
data class BetWithEvents (
    @Embedded val bet: Bet,
    @Relation(
        parentColumn = "betId",
        entityColumn = "eventId",
        associateBy = Junction(BetWithEventCrossRef::class)
    )
    val events: List<Event>
)