package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

data class Competitor(
    @SerializedName("errors")
    val errors: Int,

    @SerializedName("hits")
    val hits: Int,

    @SerializedName("linescores")
    val linescores: List<LineScoreValue>?,

    @SerializedName("score")
    val score: String,

    @SerializedName("team")
    val team: Team
) {
    data class LineScoreValue(
        @SerializedName("value")
        val value: Int
    )
}
