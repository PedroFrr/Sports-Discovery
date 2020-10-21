package com.pedrofr.sportsfinder.networking.response

import com.squareup.moshi.Json

data class OddsValueResponse(
    @field:Json(name = "h2h") val h2h: List<Double>
)