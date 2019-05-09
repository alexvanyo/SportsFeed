package com.alexvanyo.sportsfeed.api.baseball

import com.alexvanyo.sportsfeed.api.Event
import com.google.gson.annotations.SerializedName

/**
 * Data class that corresponds to a single baseball event, which may contain multiple competitions.
 */
data class BaseballEvent(
    @SerializedName("competitions")
    override val competitions: List<BaseballCompetition>,

    @SerializedName("name")
    override val name: String
) : Event
