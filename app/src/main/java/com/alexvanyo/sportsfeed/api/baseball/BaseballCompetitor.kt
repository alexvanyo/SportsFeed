package com.alexvanyo.sportsfeed.api.baseball

import com.alexvanyo.sportsfeed.api.Competitor
import com.alexvanyo.sportsfeed.api.Team
import com.google.gson.annotations.SerializedName

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

    @SerializedName("team")
    override val team: Team
): Competitor {
    data class LineScoreValue(
        @SerializedName("value")
        val value: Int
    )
}