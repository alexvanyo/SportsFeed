package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

/**
 * Data class that is the main object returned by an ESPN scoreboard API request
 */
data class ScoreboardData (
    @SerializedName("events")
    val events: List<Event>
)
