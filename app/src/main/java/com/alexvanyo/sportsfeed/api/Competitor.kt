package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

data class Competitor(
    @SerializedName("score")
    val score: String,

    @SerializedName("team")
    val team: Team
)