package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

data class Statistic(
    @SerializedName("abbreviation")
    val abbreviation: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("displayValue")
    val displayValue: String
)