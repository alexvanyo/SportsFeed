package com.alexvanyo.sportsfeed.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Interface for the main object returned by an ESPN scoreboard API request
 */
sealed class ScoreboardData : Parcelable {
    abstract val events: List<Event>
}

/**
 * Data class for an arbitrary ESPN scoreboard API request
 */
@Parcelize
data class DefaultScoreboardData(
    @SerializedName("events")
    override val events: List<DefaultEvent>
) : ScoreboardData()

/**
 * Data class that is the main object returned by an baseball ESPN scoreboard API request
 */
@Parcelize
data class BaseballScoreboardData(
    @SerializedName("events")
    override val events: List<BaseballEvent>
) : ScoreboardData()

/**
 * Data class that is the main object returned by a soccer ESPN scoreboard API request
 */
@Parcelize
data class SoccerScoreboardData(
    @SerializedName("events")
    override val events: List<SoccerEvent>
) : ScoreboardData()
