package com.alexvanyo.sportsfeed.ui

import TestUtil
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.TestSportsFeedApp
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.Competitor
import com.alexvanyo.sportsfeed.api.Status
import com.alexvanyo.sportsfeed.databinding.CompetitionItemBinding
import com.alexvanyo.sportsfeed.util.DataBoundViewHolder
import com.alexvanyo.sportsfeed.util.mock
import com.alexvanyo.sportsfeed.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.competition_item.view.*
import kotlinx.android.synthetic.main.feed_fragment.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestSportsFeedApp::class)
class FeedFragmentTest {
    private val mockFeedViewModel: FeedViewModel = mock()
    private val mockNavController: NavController = mock()

    private lateinit var feedFragmentScenario: FragmentScenario<FeedFragment>

    private val competitions = MutableLiveData<List<Competition>>()

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
        ), status = TestUtil.createStatus(TestUtil.createStatusType(shortDetail = "Short Detail"))
    )

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<TestSportsFeedApp>()
        `when`(app.viewModelFactory.create(FeedViewModel::class.java)).thenReturn(mockFeedViewModel)
        `when`(mockFeedViewModel.competitions).thenReturn(competitions)
        feedFragmentScenario = launchFragmentInContainer<FeedFragment>()

        feedFragmentScenario.onFragment {
            Navigation.setViewNavController(it.requireView(), mockNavController)
        }
    }

    @Test
    fun `recyclerView is visible`() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun `recyclerView is initially empty`() {
        feedFragmentScenario.onFragment {
            assertEquals(0, it.recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun `recyclerView is populated when data is available`() {
        competitions.postValue(listOf(TestUtil.createDefaultCompetition()))

        feedFragmentScenario.onFragment {
            assertEquals(1, it.recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun `left abbreviation is correct`() {
        competitions.postValue(listOf(testDisplayingCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.recyclerView[0].leftAbbr.visibility)
            assertEquals(testDisplayingCompetition.getLeftTeam().team.abbreviation, it.recyclerView[0].leftAbbr.text)
        }
    }

    @Test
    fun `left score is correct`() {
        competitions.postValue(listOf(testDisplayingCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.recyclerView[0].leftScore.visibility)
            assertEquals(testDisplayingCompetition.getLeftTeam().score, it.recyclerView[0].leftScore.text)
        }
    }

    @Test
    fun `divider is visible`() {
        competitions.postValue(listOf(testDisplayingCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.recyclerView[0].divider.visibility)
        }
    }

    @Test
    fun `right abbreviation is correct`() {
        competitions.postValue(listOf(testDisplayingCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.recyclerView[0].rightAbbr.visibility)
            assertEquals(testDisplayingCompetition.getRightTeam().team.abbreviation, it.recyclerView[0].rightAbbr.text)
        }
    }

    @Test
    fun `right score is correct`() {
        competitions.postValue(listOf(testDisplayingCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.recyclerView[0].rightScore.visibility)
            assertEquals(testDisplayingCompetition.getRightTeam().score, it.recyclerView[0].rightScore.text)
        }
    }

    @Test
    fun `status is correct`() {
        competitions.postValue(listOf(testDisplayingCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.recyclerView[0].status.visibility)
            assertEquals(testDisplayingCompetition.status.type.shortDetail, it.recyclerView[0].status.text)
        }
    }

    @Test
    fun `multiple competitions are both displayed`() {
        competitions.postValue(
            listOf(
                TestUtil.createDefaultCompetition(uid = "1"),
                TestUtil.createDefaultCompetition(uid = "2")
            )
        )

        feedFragmentScenario.onFragment {
            assertEquals(2, it.recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun `multiple posts with the same id result in only one displayed`() {
        val competition = TestUtil.createDefaultCompetition(uid = "1")

        competitions.postValue(listOf(competition))
        competitions.postValue(listOf(competition))

        feedFragmentScenario.onFragment {
            assertEquals(1, it.recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun `multiple posts with the different id result in both displayed`() {
        val firstCompetition = TestUtil.createDefaultCompetition(uid = "1")
        val secondCompetition = TestUtil.createDefaultCompetition(uid = "2")

        competitions.postValue(listOf(firstCompetition))
        competitions.postValue(listOf(firstCompetition, secondCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(2, it.recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun `multiple competitions displayed in order`() {

        val inCompetition = TestUtil.createDefaultCompetition(
            status = TestUtil.createStatus(
                TestUtil.createStatusType(
                    shortDetail = "IN",
                    state = Status.Type.State.IN
                )
            )
        )
        val postCompetition = TestUtil.createDefaultCompetition(
            status = TestUtil.createStatus(
                TestUtil.createStatusType(
                    shortDetail = "POST",
                    state = Status.Type.State.POST
                )
            )
        )
        val preCompetition = TestUtil.createDefaultCompetition(
            status = TestUtil.createStatus(
                TestUtil.createStatusType(
                    shortDetail = "PRE",
                    state = Status.Type.State.PRE
                )
            )
        )

        competitions.postValue(listOf(inCompetition, postCompetition, preCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(postCompetition.status.type.shortDetail, it.recyclerView[0].status.text)
            assertEquals(inCompetition.status.type.shortDetail, it.recyclerView[1].status.text)
            assertEquals(preCompetition.status.type.shortDetail, it.recyclerView[2].status.text)
        }
    }

    @Test
    fun `scheduled competitions hide the score views`() {
        val postCompetition = TestUtil.createDefaultCompetition(
            status = TestUtil.createStatus(
                TestUtil.createStatusType(
                    state = Status.Type.State.PRE
                )
            )
        )

        competitions.postValue(listOf(postCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.INVISIBLE, it.recyclerView[0].leftScore.visibility)
            assertEquals(View.INVISIBLE, it.recyclerView[0].divider.visibility)
            assertEquals(View.INVISIBLE, it.recyclerView[0].rightScore.visibility)
        }
    }

    @Test
    fun `single competition triggers on click`() {
        competitions.postValue(listOf(TestUtil.createDefaultCompetition(uid = "1")))

        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<DataBoundViewHolder<CompetitionItemBinding>>(
                0,
                click()
            )
        )

        verify(mockFeedViewModel).selectCompetition("1")
        verify(mockNavController).navigate(FeedFragmentDirections.actionFeedFragmentToCompetitionFragment())
    }

    @Test
    fun `new competition bumping another one out doesn't trigger wrong click on old competition`() {

        val preCompetition = TestUtil.createDefaultCompetition(
            uid = "1",
            status = TestUtil.createStatus(TestUtil.createStatusType(state = Status.Type.State.PRE))
        )

        val postCompetition = TestUtil.createDefaultCompetition(
            uid = "2",
            status = TestUtil.createStatus(TestUtil.createStatusType(state = Status.Type.State.POST))
        )

        competitions.postValue(listOf(preCompetition))
        competitions.postValue(listOf(preCompetition, postCompetition))

        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<DataBoundViewHolder<CompetitionItemBinding>>(
                1,
                click()
            )
        )

        verify(mockFeedViewModel).selectCompetition("1")
    }
}