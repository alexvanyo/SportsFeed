package com.alexvanyo.sportsfeed.api

import com.alexvanyo.sportsfeed.api.baseball.BaseballCompetition
import com.alexvanyo.sportsfeed.api.baseball.BaseballCompetitor
import com.alexvanyo.sportsfeed.api.baseball.NUMBER_INNINGS
import createBaseballCompetition
import createBaseballCompetitor
import org.junit.Assert
import org.junit.Test

class BaseballCompetitionTest {

    /**
     * Helper test function that verifies a box score.
     */
    private fun assertBoxScore(
        expectedAway: List<String>,
        expectedHome: List<String>,
        boxScore: List<BaseballCompetition.Inning>
    ) {
        // The expected lists should have the same size
        assert(expectedAway.size == expectedHome.size)

        Assert.assertTrue("box score has less than 9 innings", boxScore.size >= NUMBER_INNINGS)
        Assert.assertTrue("box score has the wrong size", expectedAway.size == boxScore.size)

        boxScore.forEachIndexed { index, inning ->
            // The innings should be correctly indexed in order
            Assert.assertEquals("inning ${index + 1} has the wrong label", (index + 1).toString(), inning.inningLabel)
            Assert.assertEquals(
                "inning ${index + 1} has the wrong away team runs",
                expectedAway[index],
                inning.awayTeamRuns
            )
            Assert.assertEquals(
                "inning ${index + 1} has the wrong home team runs",
                expectedHome[index],
                inning.homeTeamRuns
            )
        }
    }

    @Test
    fun `null linescores for first competitor doesn't crash`() {
        val competition = createBaseballCompetition(
            competitors = listOf(
                createBaseballCompetitor(linescores = null),
                createBaseballCompetitor(linescores = listOf(BaseballCompetitor.LineScoreValue(0)))
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
        val competition = createBaseballCompetition(
            competitors = listOf(
                createBaseballCompetitor(linescores = listOf(BaseballCompetitor.LineScoreValue(0))),
                createBaseballCompetitor(linescores = null)
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
        val competition = createBaseballCompetition(
            competitors = listOf(
                createBaseballCompetitor(linescores = null),
                createBaseballCompetitor(linescores = null)
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
        val competition = createBaseballCompetition(
            competitors = listOf(
                createBaseballCompetitor(linescores = listOf(
                    BaseballCompetitor.LineScoreValue(1),
                    BaseballCompetitor.LineScoreValue(3),
                    BaseballCompetitor.LineScoreValue(5),
                    BaseballCompetitor.LineScoreValue(7)
                )),
                createBaseballCompetitor(linescores = listOf(
                    BaseballCompetitor.LineScoreValue(2),
                    BaseballCompetitor.LineScoreValue(4),
                    BaseballCompetitor.LineScoreValue(6),
                    BaseballCompetitor.LineScoreValue(8),
                    BaseballCompetitor.LineScoreValue(10)
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
        val competition = createBaseballCompetition(
            competitors = listOf(
                createBaseballCompetitor(linescores = listOf(
                    BaseballCompetitor.LineScoreValue(1),
                    BaseballCompetitor.LineScoreValue(3),
                    BaseballCompetitor.LineScoreValue(5),
                    BaseballCompetitor.LineScoreValue(7),
                    BaseballCompetitor.LineScoreValue(9),
                    BaseballCompetitor.LineScoreValue(11),
                    BaseballCompetitor.LineScoreValue(13),
                    BaseballCompetitor.LineScoreValue(15),
                    BaseballCompetitor.LineScoreValue(17)
                )),
                createBaseballCompetitor(linescores = listOf(
                    BaseballCompetitor.LineScoreValue(2),
                    BaseballCompetitor.LineScoreValue(4),
                    BaseballCompetitor.LineScoreValue(6),
                    BaseballCompetitor.LineScoreValue(8),
                    BaseballCompetitor.LineScoreValue(10),
                    BaseballCompetitor.LineScoreValue(12),
                    BaseballCompetitor.LineScoreValue(14),
                    BaseballCompetitor.LineScoreValue(16),
                    BaseballCompetitor.LineScoreValue(18)
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
        val competition = createBaseballCompetition(
            competitors = listOf(
                createBaseballCompetitor(linescores = listOf(
                    BaseballCompetitor.LineScoreValue(1),
                    BaseballCompetitor.LineScoreValue(3),
                    BaseballCompetitor.LineScoreValue(5),
                    BaseballCompetitor.LineScoreValue(7),
                    BaseballCompetitor.LineScoreValue(9),
                    BaseballCompetitor.LineScoreValue(11),
                    BaseballCompetitor.LineScoreValue(13),
                    BaseballCompetitor.LineScoreValue(15),
                    BaseballCompetitor.LineScoreValue(17),
                    BaseballCompetitor.LineScoreValue(19),
                    BaseballCompetitor.LineScoreValue(21)
                )),
                createBaseballCompetitor(linescores = listOf(
                    BaseballCompetitor.LineScoreValue(2),
                    BaseballCompetitor.LineScoreValue(4),
                    BaseballCompetitor.LineScoreValue(6),
                    BaseballCompetitor.LineScoreValue(8),
                    BaseballCompetitor.LineScoreValue(10),
                    BaseballCompetitor.LineScoreValue(12),
                    BaseballCompetitor.LineScoreValue(14),
                    BaseballCompetitor.LineScoreValue(16),
                    BaseballCompetitor.LineScoreValue(18),
                    BaseballCompetitor.LineScoreValue(20),
                    BaseballCompetitor.LineScoreValue(22)
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
