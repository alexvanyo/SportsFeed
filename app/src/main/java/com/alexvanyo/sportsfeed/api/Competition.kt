package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.math.max

/**
 * Interface that corresponds to a single competition, which is a game played between two teams.
 */
sealed class Competition : Comparable<Competition> {
    abstract val competitors: List<Competitor>
    abstract val headlines: List<Headline>?
    abstract val startDate: Date
    abstract val status: Status
    abstract val uid: String

    /**
     * A competition is "less" than another competition if it should appear earlier in a list of games that have
     * finished, are in progress, and have yet to start.
     */
    override fun compareTo(other: Competition): Int {
        // Finished competitions should come before in progress competitions,
        // which should come before scheduled competitions
        val stateComparison = this.status.type.state.compareTo(other.status.type.state)
        if (stateComparison != 0) {
            return stateComparison
        }

        // Competitions in the same state should be order by start time
        return this.startDate.compareTo(other.startDate)
    }

    /**
     * Returns the home team.
     */
    fun getHomeTeam(): Competitor {
        return competitors.first { it.homeAway == Competitor.HomeAway.HOME }
    }

    /**
     * Returns the away team.
     */
    fun getAwayTeam(): Competitor {
        return competitors.first { it.homeAway == Competitor.HomeAway.AWAY }
    }

    /**
     * Returns the team that should be displayed first visually.
     * For most American sports, this is the away team, which is the default implementation.
     */
    open fun getFirstTeam() = getAwayTeam()

    /**
     * Returns the team that should be displayed second visually.
     * For most American sports, this is the home team, which is the default implementation.
     */
    open fun getSecondTeam() = getHomeTeam()

    /**
     * Returns a list of paired statistics for the competition.
     */
    fun getPairedStatistics(): List<PairedStatistic> {
        return getFirstTeam().statistics?.associateBy { it.name }.orEmpty().let { firstStatisticMap ->
            getSecondTeam().statistics.orEmpty()
                .filter { firstStatisticMap.containsKey(it.name) }
                .map {
                    PairedStatistic(
                        it.abbreviation,
                        firstStatisticMap.getValue(it.name).displayValue,
                        it.displayValue
                    )
                }
        }
    }

    data class PairedStatistic(
        val name: String,
        val firstDisplayValue: String,
        val secondDisplayValue: String
    )
}

/**
 * Default data class that can represent any competition
 */
data class DefaultCompetition(
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
) : Competition()

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
) : Competition() {

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
) : Competition() {
    /**
     * For soccer games, the first team displayed is the home team.
     */
    override fun getFirstTeam(): Competitor = getHomeTeam()

    /**
     * For soccer games, the second team displayed is the away team.
     */
    override fun getSecondTeam(): Competitor = getAwayTeam()
}