package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

data class Team (
    @SerializedName("abbreviation")
    val abbreviation: String,

    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("shortDisplayName")
    val shortDisplayName: String,

    @SerializedName("logo")
    val logo: String
)