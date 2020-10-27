package com.pedrofr.sportsfinder.networking.response

import com.squareup.moshi.Json

data class GetEventsResponse(
    @field:Json(name="success") val success: Boolean,
    @field:Json(name="data") val results: List<EventResponse>
)