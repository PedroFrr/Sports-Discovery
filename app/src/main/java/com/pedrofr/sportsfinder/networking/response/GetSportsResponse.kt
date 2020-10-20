package com.pedrofr.sportsfinder.networking.response

import com.google.gson.annotations.SerializedName

data class GetSportsResponse(
    @field:SerializedName("success") val success: Boolean,
    @field:SerializedName("data") val results: List<SportsResponse>
)