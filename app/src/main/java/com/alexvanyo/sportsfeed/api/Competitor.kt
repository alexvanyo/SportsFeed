package com.alexvanyo.sportsfeed.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

sealed class Competitor : Parcelable {
    abstract val homeAway: HomeAway
    abstract val score: String
    abstract val statistics: List<Statistic>?
    abstract val team: Team

    enum class HomeAway {
        @SerializedName("home")
        HOME,
        @SerializedName("away")
        AWAY,
    }
}

@Parcelize
data class DefaultCompetitor(
    @SerializedName("homeAway")
    override val homeAway: HomeAway,

    @SerializedName("score")
    override val score: String,

    @SerializedName("statistics")
    override val statistics: List<Statistic>?,

    @SerializedName("team")
    override val team: Team
) : Competitor()

@Parcelize
data class BaseballCompetitor(
    @SerializedName("errors")
    val errors: Int,

    @SerializedName("hits")
    val hits: Int,

    @SerializedName("homeAway")
    override val homeAway: HomeAway,

    @SerializedName("linescores")
    val linescores: List<LineScoreValue>?,

    @SerializedName("score")
    override val score: String,

    @SerializedName("statistics")
    override val statistics: List<Statistic>?,

    @SerializedName("team")
    override val team: Team
) : Competitor() {
    @Parcelize
    data class LineScoreValue(
        @SerializedName("value")
        val value: Int
    ) : Parcelable
}
