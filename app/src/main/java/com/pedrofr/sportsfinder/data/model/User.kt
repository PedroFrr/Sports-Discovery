package com.pedrofr.sportsfinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(
    @PrimaryKey val userId: String = UUID.randomUUID().toString(),
    val username: String,
    val balance: Double = 0.0,
    val numberOfBets: Int = 0
)