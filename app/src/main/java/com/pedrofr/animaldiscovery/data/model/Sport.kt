package com.pedrofr.animaldiscovery.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Sport(
    @PrimaryKey
    val sportsKey: UUID = UUID.randomUUID(),
    val title: String,
    val imageUrl: String = ""
) {
}