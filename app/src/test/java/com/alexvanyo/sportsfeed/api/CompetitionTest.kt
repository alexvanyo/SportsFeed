package com.alexvanyo.sportsfeed.api

import TestUtil
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*
import kotlin.math.sign

class CompetitionTest {
    @Test
    fun `home team is correct`() {
        val homeCompetitor = TestUtil.createDefaultCompetitor(homeAway = Competitor.HomeAway.HOME)
        val awayCompetitor = TestUtil.createDefaultCompetitor(homeAway = Competitor.HomeAway.AWAY)

        val competition = TestUtil.createDefaultCompetition(listOf(homeCompetitor, awayCompetitor))

        assertEquals(homeCompetitor, competition.getHomeTeam())
    }

    @Test
    fun `away team is correct`() {
        val homeCompetitor = TestUtil.createDefaultCompetitor(homeAway = Competitor.HomeAway.HOME)
        val awayCompetitor = TestUtil.createDefaultCompetitor(homeAway = Competitor.HomeAway.AWAY)

        val competition = TestUtil.createDefaultCompetition(listOf(homeCompetitor, awayCompetitor))

        assertEquals(awayCompetitor, competition.getAwayTeam())
    }

    @Test
    fun `null home statistics results in an empty list`() {
        val homeCompetitor = TestUtil.createDefaultCompetitor(
            homeAway = Competitor.HomeAway.HOME,
            statistics = null
        )
        val awayCompetitor = TestUtil.createDefaultCompetitor(
            homeAway = Competitor.HomeAway.AWAY,
            statistics = listOf(Statistic("t", "test", "0"))
        )

        val competition = TestUtil.createDefaultCompetition(listOf(homeCompetitor, awayCompetitor))

        assertEquals(emptyList<Competition.PairedStatistic>(), competition.getPairedStatistics())
    }

    @Test
    fun `null away statistics results in an empty list`() {
        val homeCompetitor = TestUtil.createDefaultCompetitor(
            homeAway = Competitor.HomeAway.HOME,
            statistics = listOf(Statistic("t", "test", "0"))
        )
        val awayCompetitor = TestUtil.createDefaultCompetitor(
            homeAway = Competitor.HomeAway.AWAY,
            statistics = null
        )

        val competition = TestUtil.createDefaultCompetition(listOf(homeCompetitor, awayCompetitor))

        assertEquals(emptyList<Competition.PairedStatistic>(), competition.getPairedStatistics())
    }

    @Test
    fun `null statistics for both results in an empty list`() {
        val homeCompetitor = TestUtil.createDefaultCompetitor(
            homeAway = Competitor.HomeAway.HOME,
            statistics = null
        )
        val awayCompetitor = TestUtil.createDefaultCompetitor(
            homeAway = Competitor.HomeAway.AWAY,
            statistics = null
        )

        val competition = TestUtil.createDefaultCompetition(listOf(homeCompetitor, awayCompetitor))

        assertEquals(emptyList<Competition.PairedStatistic>(), competition.getPairedStatistics())
    }

    @Test
    fun `paired statistics only contain statistics that both competitors have`() {
        val homeCompetitor = TestUtil.createDefaultCompetitor(
            homeAway = Competitor.HomeAway.HOME,
            statistics = listOf(
                Statistic("shared", "sharedStatistic", "right1"),
                Statistic("right", "rightOnlyStatistic", "right2")
            )
        )
        val awayCompetitor = TestUtil.createDefaultCompetitor(
            homeAway = Competitor.HomeAway.AWAY,
            statistics = listOf(
                Statistic("shared", "sharedStatistic", "left1"),
                Statistic("left", "leftOnlyStatistic", "left2")
            )
        )

        val competition = TestUtil.createDefaultCompetition(listOf(homeCompetitor, awayCompetitor))

        assertEquals(
            listOf(Competition.PairedStatistic("left1", "shared", "right1")),
            competition.getPairedStatistics()
        )
    }
}

@RunWith(Parameterized::class)
class ParameterizedCompetitionTest(
    private val firstCompetition: Competition,
    private val secondCompetition: Competition,
    private val comparison: Int
) {

    /**
     * Helper data class to store the parameters to generate the sorted competition list.
     */
    private data class CompetitionSortingParameters(
        val state: Status.Type.State,
        val startDate: Date
    ) {
        /**
         * Create a competition with default values except for those specified.
         */
        fun createCompetition() = TestUtil.createDefaultCompetition(
            status = TestUtil.createStatus(TestUtil.createStatusType(state = state)),
            startDate = startDate
        )
    }

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0}.compareTo({1}) = {2}")
        fun data(): Iterable<Array<Any>> = listOf(
            // Setup the list of properly sorted competitions
            CompetitionSortingParameters(Status.Type.State.POST, Date(2019, 4, 1)),
            CompetitionSortingParameters(Status.Type.State.POST, Date(2019, 4, 4)),
            CompetitionSortingParameters(Status.Type.State.POST, Date(2019, 4, 7)),
            CompetitionSortingParameters(Status.Type.State.IN, Date(2019, 4, 2)),
            CompetitionSortingParameters(Status.Type.State.IN, Date(2019, 4, 5)),
            CompetitionSortingParameters(Status.Type.State.IN, Date(2019, 4, 8)),
            CompetitionSortingParameters(Status.Type.State.PRE, Date(2019, 4, 3)),
            CompetitionSortingParameters(Status.Type.State.PRE, Date(2019, 4, 6)),
            CompetitionSortingParameters(Status.Type.State.PRE, Date(2019, 4, 9))
        ).map { parameters -> parameters.createCompetition() }.let {
            // Construct the Cartesian product of the sorted competitions, with the appropriate comparison value
            it.indices.flatMap { i ->
                it.indices.map { j ->
                    arrayOf<Any>(
                        it[i], it[j], i.compareTo(j)
                    )
                }
            }
        }
    }

    @Test
    fun compareTo() {
        assertEquals(comparison, firstCompetition.compareTo(secondCompetition).sign)
    }
}
