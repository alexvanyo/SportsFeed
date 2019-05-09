package com.alexvanyo.sportsfeed.api.soccer

import com.alexvanyo.sportsfeed.api.Event
import com.google.gson.annotations.SerializedName

/**
 * Data class that corresponds to a single baseball event, which may contain multiple competitions.
 */
data class SoccerEvent(
    @SerializedName("competitions")
    override val competitions: List<SoccerCompetition>,

    @SerializedName("name")
    override val name: String
): Event