package com.pedrofr.sportsfinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(
    @PrimaryKey val userId: String = "1", //TODO since we don't have users we set a static value to User
    val balance: Double = 0.0,
    val numberOfBets: Int
)