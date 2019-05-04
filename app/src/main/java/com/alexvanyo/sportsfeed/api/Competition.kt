package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Data class that corresponds to a single competition, which is usually a game played between two teams.
 */
data class Competition(
    @SerializedName("competitors")
    val competitors: List<Competitor>,

    @SerializedName("startDate")
    val startDate: Date,

    @SerializedName("status")
    val status: Status,

    @SerializedName("uid")
    val uid: String
) : Comparable<Competition> {
    /**
     * A competition is "less" than another competition if it should appear earlier in a list of games that have
     * finished, are in progress, and have yet to start.
     */
    override fun compareTo(other: Competition): Int {
        // Finished competitions should come before in progress competitions,
        // which should come before schedule competitions
        val stateComparison = this.status.type.state.compareTo(other.status.type.state)
        if (stateComparison != 0) {
            return stateComparison
        }

        // Competitions in the same state should be order by start time
        return this.startDate.compareTo(other.startDate)
    }
}