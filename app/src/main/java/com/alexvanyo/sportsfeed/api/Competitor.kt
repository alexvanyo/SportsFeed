package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

interface Competitor {
    val score: String
    val team: Team
}

data class DefaultCompetitor(
    @SerializedName("score")
    override val score: String,

    @SerializedName("team")
    override val team: Team
): Competitor
