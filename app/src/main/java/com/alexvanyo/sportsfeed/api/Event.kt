package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

/**
 * Interface that corresponds to a single event, which may contain multiple competitions.
 */
sealed class Event {
    abstract val competitions: List<Competition>
    abstract val name: String
}

/**
 * Default data class that can represent any competition
 */
data class DefaultEvent(
    @SerializedName("competitions")
    override val competitions: List<DefaultCompetition>,

    @SerializedName("name")
    override val name: String
) : Event()

/**
 * Data class that corresponds to a single baseball event, which may contain multiple competitions.
 */
data class BaseballEvent(
    @SerializedName("competitions")
    override val competitions: List<BaseballCompetition>,

    @SerializedName("name")
    override val name: String
) : Event()

/**
 * Data class that corresponds to a single baseball event, which may contain multiple competitions.
 */
data class SoccerEvent(
    @SerializedName("competitions")
    override val competitions: List<SoccerCompetition>,

    @SerializedName("name")
    override val name: String
) : Event()
