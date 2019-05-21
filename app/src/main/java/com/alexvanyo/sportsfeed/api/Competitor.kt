package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

sealed class Competitor {
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

data class DefaultCompetitor(
    @SerializedName("homeAway")
    override val homeAway: Competitor.HomeAway,

    @SerializedName("score")
    override val score: String,

    @SerializedName("statistics")
    override val statistics: List<Statistic>?,

    @SerializedName("team")
    override val team: Team
) : Competitor()

data class BaseballCompetitor(
    @SerializedName("errors")
    val errors: Int,

    @SerializedName("hits")
    val hits: Int,

    @SerializedName("homeAway")
    override val homeAway: Competitor.HomeAway,

    @SerializedName("linescores")
    val linescores: List<LineScoreValue>?,

    @SerializedName("score")
    override val score: String,

    @SerializedName("statistics")
    override val statistics: List<Statistic>?,

    @SerializedName("team")
    override val team: Team
) : Competitor() {
    data class LineScoreValue(
        @SerializedName("value")
        val value: Int
    )
}
