package com.alexvanyo.sportsfeed.api.baseball

import com.alexvanyo.sportsfeed.api.ScoreboardData
import com.google.gson.annotations.SerializedName

/**
 * Data class that is the main object returned by an baseball ESPN scoreboard API request
 */
data class BaseballScoreboardData(
    @SerializedName("events")
    override val events: List<BaseballEvent>
): ScoreboardData