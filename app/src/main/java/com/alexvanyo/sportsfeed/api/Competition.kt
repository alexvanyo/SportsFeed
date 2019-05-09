package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Interface that corresponds to a single competition, which is a game played between two teams.
 */
interface Competition : Comparable<Competition> {
    val competitors: List<Competitor>
    val startDate: Date
    val status: Status
    val uid: String

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
     * Returns the team that should be displayed on the left visually.
     * For most American sports, this is the away team, which is the default implementation.
     */
    fun getLeftTeam() = getAwayTeam()

    /**
     * Returns the team that should be displayed on the right visually.
     * For most American sports, this is the home team, which is the default implementation.
     */
    fun getRightTeam() = getHomeTeam()

    /**
     * Returns a list of paired statistics for the competition.
     */
    fun getPairedStatistics(): List<PairedStatistic> {
        return getLeftTeam().statistics?.associateBy { it.name }.orEmpty().let { leftStatisticMap ->
            getRightTeam().statistics.orEmpty()
                .filter { leftStatisticMap.containsKey(it.name) }
                .map {
                    PairedStatistic(
                        leftStatisticMap.getValue(it.name).displayValue,
                        it.abbreviation,
                        it.displayValue
                    )
                }
        }
    }

    data class PairedStatistic(
        val leftDisplayValue: String,
        val name: String,
        val rightDisplayValue: String
    )
}

/**
 * Default data class that can represent any competition
 */
data class DefaultCompetition(
    @SerializedName("competitors")
    override val competitors: List<DefaultCompetitor>,

    @SerializedName("startDate")
    override val startDate: Date,

    @SerializedName("status")
    override val status: Status,

    @SerializedName("uid")
    override val uid: String
) : Competition