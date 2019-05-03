package com.alexvanyo.sportsfeed.api

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*
import kotlin.math.sign

@RunWith(Parameterized::class)
class CompetitionTest(
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

        // Setup the list of properly sorted competitions
        private val competitionParameters: List<CompetitionSortingParameters> = listOf(
            CompetitionSortingParameters(Status.Type.State.POST, Date(2019, 4, 1)),
            CompetitionSortingParameters(Status.Type.State.POST, Date(2019, 4, 4)),
            CompetitionSortingParameters(Status.Type.State.POST, Date(2019, 4, 7)),
            CompetitionSortingParameters(Status.Type.State.IN, Date(2019, 4, 2)),
            CompetitionSortingParameters(Status.Type.State.IN, Date(2019, 4, 5)),
            CompetitionSortingParameters(Status.Type.State.IN, Date(2019, 4, 8)),
            CompetitionSortingParameters(Status.Type.State.PRE, Date(2019, 4, 3)),
            CompetitionSortingParameters(Status.Type.State.PRE, Date(2019, 4, 6)),
            CompetitionSortingParameters(Status.Type.State.PRE, Date(2019, 4, 9))
        )

        // Construct the competitions
        private val sortedCompetitions = competitionParameters.map { parameters -> parameters.createCompetition() }

        // Construct the Cartesian product of the sorted competitions, with the appropriate comparison value
        private val data = sortedCompetitions.indices.flatMap { i ->
            sortedCompetitions.indices.map { j ->
                arrayOf<Any>(
                    sortedCompetitions[i], sortedCompetitions[j], i.compareTo(j)
                )
            }
        }

        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0}.compareTo({1}) = {2}")
        fun data(): Iterable<Array<Any>> = data
    }

    @Test
    fun compareTo() {
        assertEquals(comparison, firstCompetition.compareTo(secondCompetition).sign)
    }
}