package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

data class ScoreboardData (
    @SerializedName("events")
    val events: List<Event>
)

data class Event (
    @SerializedName("name")
    val name: String
)

