package com.pedrofr.animaldiscovery.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class GetSportsResponse(val results: List<SportsResponse>)