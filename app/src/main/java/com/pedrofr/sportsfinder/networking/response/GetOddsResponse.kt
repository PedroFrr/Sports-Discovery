package com.pedrofr.sportsfinder.networking.response

import com.squareup.moshi.Json

data class GetOddsResponse(
    @field:Json(name="success") val success: Boolean,
    @field:Json(name="data") val results: List<OddsResponse>
)