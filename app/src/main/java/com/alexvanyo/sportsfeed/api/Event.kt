package com.alexvanyo.sportsfeed.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Interface that corresponds to a single event, which may contain multiple competitions.
 */
sealed class Event : Parcelable {
    abstract val competitions: List<Competition>
    abstract val name: String
}

/**
 * Default data class that can represent any competition
 */
@Parcelize
data class DefaultEvent(
    @SerializedName("competitions")
    override val competitions: List<DefaultCompetition>,

    @SerializedName("name")
    override val name: String
) : Event()

/**
 * Data class that corresponds to a single baseball event, which may contain multiple competitions.
 */
@Parcelize
data class BaseballEvent(
    @SerializedName("competitions")
    override val competitions: List<BaseballCompetition>,

    @SerializedName("name")
    override val name: String
) : Event()

/**
 * Data class that corresponds to a single baseball event, which may contain multiple competitions.
 */
@Parcelize
data class SoccerEvent(
    @SerializedName("competitions")
    override val competitions: List<SoccerCompetition>,

    @SerializedName("name")
    override val name: String
) : Event()
