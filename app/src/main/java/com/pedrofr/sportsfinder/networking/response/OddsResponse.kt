package com.pedrofr.sportsfinder.networking.response

import com.squareup.moshi.Json

data class OddsResponse(
    @field:Json(name = "sport_key") val sportsKey: String,
    @field:Json(name = "teams") val teams: List<String>,
    @field:Json(name = "commence_time") val startTime: Long,
    @field:Json(name = "sites") val sites: List<SitesResponse>
)