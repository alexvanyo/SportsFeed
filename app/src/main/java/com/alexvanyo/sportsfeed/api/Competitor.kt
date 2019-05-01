package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

data class Competitor (
    @SerializedName("team")
    val team: Team,

    @SerializedName("score")
    val score: String
)