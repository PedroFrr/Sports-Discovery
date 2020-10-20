package com.pedrofr.sportsfinder.networking.response

import com.google.gson.annotations.SerializedName

data class SportsResponse(
    @field:SerializedName("key") val key: String,
    @field:SerializedName("active") val active: Boolean,
    @field:SerializedName("group") val group: String,
    @field:SerializedName("details") val details:  String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("has_outrights") val has_outrights: Boolean
)