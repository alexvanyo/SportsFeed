package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

/**
 * Interface for the main object returned by an ESPN scoreboard API request
 */
interface ScoreboardData {
    val events: List<Event>
}

/**
 * Data class for an arbitrary ESPN scoreboard API request
 */
data class DefaultScoreboardData(
    @SerializedName("events")
    override val events: List<DefaultEvent>
): ScoreboardData
