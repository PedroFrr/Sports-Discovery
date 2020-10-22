package com.pedrofr.sportsfinder.networking.response

import com.squareup.moshi.Json

data class SitesResponse(
    @field:Json(name = "site_key") val siteKey: String,
    @field:Json(name = "odds") val odds: OddsValueResponse
)