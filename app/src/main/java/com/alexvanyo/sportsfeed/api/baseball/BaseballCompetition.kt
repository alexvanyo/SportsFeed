package com.alexvanyo.sportsfeed.api.baseball

import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.Headline
import com.alexvanyo.sportsfeed.api.Status
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.math.max

/**
 * The normal number of innings in a baseball game
 */
const val NUMBER_INNINGS = 9

/**
 * Data class for a baseball game.
 */
data class BaseballCompetition(
    @SerializedName("competitors")
    override val competitors: List<BaseballCompetitor>,

    @SerializedName("headlines")
    override val headlines: List<Headline>,

    @SerializedName("startDate")
    override val startDate: Date,

    @SerializedName("status")
    override val status: Status,

    @SerializedName("uid")
    override val uid: String
) : Competition {

    data class Inning(
        val inningLabel: String,
        val awayTeamRuns: String,
        val homeTeamRuns: String
    )

    /**
     * Generates the data for the box score for the baseball game.
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
                    if (competitors[0].linescores?.size ?: 0 >= i)
                        competitors[0].linescores!![i - 1].value.toString() else "-"
                )
            )
        }

        return boxScore
    }
}