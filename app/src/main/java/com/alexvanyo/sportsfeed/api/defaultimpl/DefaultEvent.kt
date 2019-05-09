package com.alexvanyo.sportsfeed.api

import com.alexvanyo.sportsfeed.api.defaultimpl.DefaultCompetition
import com.google.gson.annotations.SerializedName

/**
 * Data class that corresponds to a default single event, which may contain multiple competitions.
 */
data class DefaultEvent(
    @SerializedName("competitions")
    override val competitions: List<DefaultCompetition>,

    @SerializedName("name")
    override val name: String
): Event
