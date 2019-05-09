package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.math.max

/**
 * The normal number of innings in a baseball game
 */
const val NUMBER_INNINGS = 9

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

    data class Inning(
        val inningLabel: String,
        val awayTeamRuns: String,
        val homeTeamRuns: String
    )

    /**
     * Generates the data for the box score for the baseball competition.
     */
    fun getBoxScore(): List<Inning> {

        // Get the number of innings recorded
        val numberRecordedInnings = max(competitors[0].linescores?.size ?: 0, competitors[1].linescores?.size ?: 0)

        val boxScore = arrayListOf<Inning>()

        // Generate the innings data for either 9 innings or more if a game went into extra innings
        for (i in (1..max(numberRecordedInnings, NUMBER_INNINGS))) {
            boxScore.add(
                Inning(
                    i.toString(),
                    if (competitors[1].linescores?.size ?: 0 >= i)
                        competitors[1].linescores!![i - 1].value.toString() else "-",
                    if (competitors[0].linescores?.size ?: 0>= i)
                        competitors[0].linescores!![i - 1].value.toString() else "-"
                )
            )
        }

        return boxScore
    }
}