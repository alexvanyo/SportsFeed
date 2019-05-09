package com.alexvanyo.sportsfeed.api.soccer

import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.Competitor
import com.alexvanyo.sportsfeed.api.DefaultCompetitor
import com.alexvanyo.sportsfeed.api.Status
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Data class for a soccer game.
 */
data class SoccerCompetition(
    @SerializedName("competitors")
    override val competitors: List<DefaultCompetitor>,

    @SerializedName("startDate")
    override val startDate: Date,

    @SerializedName("status")
    override val status: Status,

    @SerializedName("uid")
    override val uid: String
) : Competition {
    /**
     * For soccer games, the left team displayed is the home team.
     */
    override fun getLeftTeam(): Competitor = getHomeTeam()

    /**
     * For soccer games, the right team displayed is the away team.
     */
    override fun getRightTeam(): Competitor = getAwayTeam()
}