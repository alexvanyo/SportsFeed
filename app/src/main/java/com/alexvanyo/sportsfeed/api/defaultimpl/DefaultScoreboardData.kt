package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

/**
 * Data class for an arbitrary ESPN scoreboard API request
 */
data class DefaultScoreboardData(
    @SerializedName("events")
    override val events: List<DefaultEvent>
): ScoreboardData
