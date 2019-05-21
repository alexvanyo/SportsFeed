package com.alexvanyo.sportsfeed.api.soccer

import com.alexvanyo.sportsfeed.api.*
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Data class for a soccer game.
 */
data class SoccerCompetition(
    @SerializedName("competitors")
    override val competitors: List<DefaultCompetitor>,

    @SerializedName("headlines")
    override val headlines: List<Headline>,

    @SerializedName("startDate")
    override val startDate: Date,

    @SerializedName("status")
    override val status: Status,

    @SerializedName("uid")
    override val uid: String
) : Competition {
    /**
     * For soccer games, the first team displayed is the home team.
     */
    override fun getFirstTeam(): Competitor = getHomeTeam()

    /**
     * For soccer games, the second team displayed is the away team.
     */
    override fun getSecondTeam(): Competitor = getAwayTeam()
}