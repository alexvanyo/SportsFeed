package com.alexvanyo.sportsfeed.api.soccer

import com.alexvanyo.sportsfeed.api.ScoreboardData
import com.google.gson.annotations.SerializedName

/**
 * Data class that is the main object returned by a soccer ESPN scoreboard API request
 */
data class SoccerScoreboardData(
    @SerializedName("events")
    override val events: List<SoccerEvent>
): ScoreboardData