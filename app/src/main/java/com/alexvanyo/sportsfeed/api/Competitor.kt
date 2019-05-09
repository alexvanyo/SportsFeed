package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

interface Competitor {
    val homeAway: HomeAway
    val score: String
    val team: Team

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

    @SerializedName("team")
    override val team: Team
) : Competitor
