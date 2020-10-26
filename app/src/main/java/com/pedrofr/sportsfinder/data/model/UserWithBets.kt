package com.pedrofr.sportsfinder.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithBets(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )
    val bets: List<Bet>
)