package com.alexvanyo.sportsfeed.api

import TestUtil
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*
import kotlin.math.sign

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
        fun createCompetition() = TestUtil.createCompetition(
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

class CompetitionTest {

    /**
     * Helper test function that verifies a box score.
     */
    private fun assertBoxScore(
        expectedAway: List<String>,
        expectedHome: List<String>,
        boxScore: List<Competition.Inning>
    ) {
        // The expected lists should have the same size
        assert(expectedAway.size == expectedHome.size)

        assertTrue("box score has less than 9 innings", boxScore.size >= NUMBER_INNINGS)
        assertTrue("box score has the wrong size", expectedAway.size == boxScore.size)

        boxScore.forEachIndexed { index, inning ->
            // The innings should be correctly indexed in order
            assertEquals("inning ${index + 1} has the wrong label", (index + 1).toString(), inning.inningLabel)
            assertEquals("inning ${index + 1} has the wrong away team runs", expectedAway[index], inning.awayTeamRuns)
            assertEquals("inning ${index + 1} has the wrong home team runs", expectedHome[index], inning.homeTeamRuns)
        }
    }

    @Test
    fun `null linescores for first competitor doesn't crash`() {
        val competition = TestUtil.createCompetition(
            competitors = listOf(
                TestUtil.createCompetitor(linescores = null),
                TestUtil.createCompetitor(linescores = listOf(Competitor.LineScoreValue(0)))
            )
        )

        assertBoxScore(
            listOf("0", "-", "-", "-", "-", "-", "-", "-", "-"),
            listOf("-", "-", "-", "-", "-", "-", "-", "-", "-"),
            competition.getBoxScore()
        )
    }

    @Test
    fun `null linescores for second competitor doesn't crash`() {
        val competition = TestUtil.createCompetition(
            competitors = listOf(
                TestUtil.createCompetitor(linescores = listOf(Competitor.LineScoreValue(0))),
                TestUtil.createCompetitor(linescores = null)
            )
        )

        assertBoxScore(
            listOf("-", "-", "-", "-", "-", "-", "-", "-", "-"),
            listOf("0", "-", "-", "-", "-", "-", "-", "-", "-"),
            competition.getBoxScore()
        )
    }

    @Test
    fun `null linescores for both competitors doesn't crash`() {
        val competition = TestUtil.createCompetition(
            competitors = listOf(
                TestUtil.createCompetitor(linescores = null),
                TestUtil.createCompetitor(linescores = null)
            )
        )

        assertBoxScore(
            listOf("-", "-", "-", "-", "-", "-", "-", "-", "-"),
            listOf("-", "-", "-", "-", "-", "-", "-", "-", "-"),
            competition.getBoxScore()
        )
    }

    @Test
    fun `linescores for a partial game`() {
        val competition = TestUtil.createCompetition(
            competitors = listOf(
                TestUtil.createCompetitor(linescores = listOf(
                    Competitor.LineScoreValue(1),
                    Competitor.LineScoreValue(3),
                    Competitor.LineScoreValue(5),
                    Competitor.LineScoreValue(7)
                )),
                TestUtil.createCompetitor(linescores = listOf(
                    Competitor.LineScoreValue(2),
                    Competitor.LineScoreValue(4),
                    Competitor.LineScoreValue(6),
                    Competitor.LineScoreValue(8),
                    Competitor.LineScoreValue(10)
                ))
            )
        )

        assertBoxScore(
            listOf("2", "4", "6", "8", "10", "-", "-", "-", "-"),
            listOf("1", "3", "5", "7", "-", "-", "-", "-", "-"),
            competition.getBoxScore()
        )
    }

    @Test
    fun `linescores for a full game`() {
        val competition = TestUtil.createCompetition(
            competitors = listOf(
                TestUtil.createCompetitor(linescores = listOf(
                    Competitor.LineScoreValue(1),
                    Competitor.LineScoreValue(3),
                    Competitor.LineScoreValue(5),
                    Competitor.LineScoreValue(7),
                    Competitor.LineScoreValue(9),
                    Competitor.LineScoreValue(11),
                    Competitor.LineScoreValue(13),
                    Competitor.LineScoreValue(15),
                    Competitor.LineScoreValue(17)
                )),
                TestUtil.createCompetitor(linescores = listOf(
                    Competitor.LineScoreValue(2),
                    Competitor.LineScoreValue(4),
                    Competitor.LineScoreValue(6),
                    Competitor.LineScoreValue(8),
                    Competitor.LineScoreValue(10),
                    Competitor.LineScoreValue(12),
                    Competitor.LineScoreValue(14),
                    Competitor.LineScoreValue(16),
                    Competitor.LineScoreValue(18)
                ))
            )
        )

        assertBoxScore(
            listOf("2", "4", "6", "8", "10", "12", "14", "16", "18"),
            listOf("1", "3", "5", "7", "9", "11", "13", "15", "17"),
            competition.getBoxScore()
        )
    }

    @Test
    fun `linescores for an extra innings game`() {
        val competition = TestUtil.createCompetition(
            competitors = listOf(
                TestUtil.createCompetitor(linescores = listOf(
                    Competitor.LineScoreValue(1),
                    Competitor.LineScoreValue(3),
                    Competitor.LineScoreValue(5),
                    Competitor.LineScoreValue(7),
                    Competitor.LineScoreValue(9),
                    Competitor.LineScoreValue(11),
                    Competitor.LineScoreValue(13),
                    Competitor.LineScoreValue(15),
                    Competitor.LineScoreValue(17),
                    Competitor.LineScoreValue(19),
                    Competitor.LineScoreValue(21)
                )),
                TestUtil.createCompetitor(linescores = listOf(
                    Competitor.LineScoreValue(2),
                    Competitor.LineScoreValue(4),
                    Competitor.LineScoreValue(6),
                    Competitor.LineScoreValue(8),
                    Competitor.LineScoreValue(10),
                    Competitor.LineScoreValue(12),
                    Competitor.LineScoreValue(14),
                    Competitor.LineScoreValue(16),
                    Competitor.LineScoreValue(18),
                    Competitor.LineScoreValue(20),
                    Competitor.LineScoreValue(22)
                ))
            )
        )

        assertBoxScore(
            listOf("2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22"),
            listOf("1", "3", "5", "7", "9", "11", "13", "15", "17", "19", "21"),
            competition.getBoxScore()
        )
    }
}
