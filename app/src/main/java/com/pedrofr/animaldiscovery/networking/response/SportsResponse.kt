package com.pedrofr.animaldiscovery.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class SportsResponse(
    val key: String,
    val active: Boolean,
    val group: String,
    val details:  String,
    val title: String
)