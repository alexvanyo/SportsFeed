package com.alexvanyo.sportsfeed.api.defaultimpl

import com.alexvanyo.sportsfeed.api.Competitor
import com.alexvanyo.sportsfeed.api.Team
import com.google.gson.annotations.SerializedName

data class DefaultCompetitor(
    @SerializedName("score")
    override val score: String,

    @SerializedName("team")
    override val team: Team
): Competitor
