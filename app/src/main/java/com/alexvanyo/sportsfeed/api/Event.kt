package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

/**
 * Interface that corresponds to a single event, which may contain multiple competitions.
 */
interface Event {
    val competitions: List<Competition>
    val name: String
}

/**
 * Default data class that can represent any competition
 */
data class DefaultEvent(
    @SerializedName("competitions")
    override val competitions: List<DefaultCompetition>,

    @SerializedName("name")
    override val name: String
) : Event
