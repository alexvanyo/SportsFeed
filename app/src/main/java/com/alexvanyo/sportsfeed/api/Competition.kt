package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Data class that corresponds to a single competition, which is usually a game played between two teams.
 */
data class Competition (
    @SerializedName("competitors")
    val competitors: List<Competitor>,

    @SerializedName("status")
    val status: Status,

    @SerializedName("uid")
    val uid: String,

    @SerializedName("date")
    val date: Date

) : Comparable<Competition> {
    override fun compareTo(other: Competition): Int {
        return this.date.compareTo(other.date)
    }
}