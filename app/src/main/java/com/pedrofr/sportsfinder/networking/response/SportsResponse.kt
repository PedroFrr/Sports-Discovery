package com.pedrofr.sportsfinder.networking.response

import com.squareup.moshi.Json

data class SportsResponse(
    @field:Json(name = "key") val key: String,
    @field:Json(name = "active") val active: Boolean,
    @field:Json(name = "group") val group: String,
    @field:Json(name = "details") val details:  String,
    @field:Json(name = "title") val title: String
)