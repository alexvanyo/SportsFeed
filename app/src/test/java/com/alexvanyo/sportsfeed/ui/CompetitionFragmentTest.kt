package com.alexvanyo.sportsfeed.ui

import TestUtil
import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexvanyo.sportsfeed.TestSportsFeedApp
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.Competitor
import com.alexvanyo.sportsfeed.api.Status
import com.alexvanyo.sportsfeed.util.mock
import com.alexvanyo.sportsfeed.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.competition_fragment.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestSportsFeedApp::class)
class CompetitionFragmentTest {
    private val mockFeedViewModel: FeedViewModel = mock()

    private lateinit var competitionFragmentScenario: FragmentScenario<CompetitionFragment>

    private val competition = MutableLiveData<Competition>()

    private val testDisplayingCompetition = TestUtil.createDefaultCompetition(
        listOf(
            TestUtil.createDefaultCompetitor(
                homeAway = Competitor.HomeAway.HOME,
                score = "1",
                team = TestUtil.createTeam("H", "Home Long Display Name", "Home Name")
            ),
            TestUtil.createDefaultCompetitor(
                homeAway = Competitor.HomeAway.AWAY,
                score = "2",
                team = TestUtil.createTeam("A", "Home Long Display Name", "Home Name")
            )
        ), status = TestUtil.createStatus(TestUtil.createStatusType(detail = "Very Long Detail"))
    )

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<TestSportsFeedApp>()
        `when`(app.viewModelFactory.create(FeedViewModel::class.java)).thenReturn(mockFeedViewModel)
        `when`(mockFeedViewModel.selectedCompetition).thenReturn(competition)
        competitionFragmentScenario = launchFragmentInContainer<CompetitionFragment>()
    }

    @Test
    fun `left name is correct`() {
        competition.postValue(testDisplayingCompetition)

        competitionFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.leftName.visibility)
            assertEquals(testDisplayingCompetition.getLeftTeam().team.shortDisplayName, it.leftName.text)
        }
    }

    @Test
    fun `left score is correct`() {
        competition.postValue(testDisplayingCompetition)

        competitionFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.leftScore.visibility)
            assertEquals(testDisplayingCompetition.getLeftTeam().score, it.leftScore.text)
        }
    }

    @Test
    fun `divider is visible`() {
        competition.postValue(testDisplayingCompetition)

        competitionFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.divider.visibility)
        }
    }

    @Test
    fun `right name is correct`() {
        competition.postValue(testDisplayingCompetition)

        competitionFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.rightName.visibility)
            assertEquals(testDisplayingCompetition.getRightTeam().team.shortDisplayName, it.rightName.text)
        }
    }

    @Test
    fun `right score is correct`() {
        competition.postValue(testDisplayingCompetition)

        competitionFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.rightScore.visibility)
            assertEquals(testDisplayingCompetition.getRightTeam().score, it.rightScore.text)
        }
    }

    @Test
    fun `status is correct`() {
        competition.postValue(testDisplayingCompetition)

        competitionFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.status.visibility)
            assertEquals(testDisplayingCompetition.status.type.detail, it.status.text)
        }
    }

    @Test
    fun `scheduled events hide the score views`() {
        val postCompetition = TestUtil.createDefaultCompetition(
            status = TestUtil.createStatus(
                TestUtil.createStatusType(
                    state = Status.Type.State.PRE
                )
            )
        )

        competition.postValue(postCompetition)

        competitionFragmentScenario.onFragment {
            assertEquals(View.INVISIBLE, it.leftScore.visibility)
            assertEquals(View.INVISIBLE, it.divider.visibility)
            assertEquals(View.INVISIBLE, it.rightScore.visibility)
        }
    }
}