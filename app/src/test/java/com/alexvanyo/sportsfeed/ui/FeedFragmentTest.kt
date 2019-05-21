package com.alexvanyo.sportsfeed.ui

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
import createDefaultCompetition
import createDefaultCompetitor
import createStatus
import createStatusType
import createTeam
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.competition_item.view.*
import kotlinx.android.synthetic.main.feed_fragment.*
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(application = TestSportsFeedApp::class)
class FeedFragmentTest {
    private val mockFeedViewModel: FeedViewModel = mock()
    private val mockNavController: NavController = mock()

    private val app = ApplicationProvider.getApplicationContext<TestSportsFeedApp>()
    private lateinit var feedFragmentScenario: FragmentScenario<FeedFragment>

    private val competitions = MutableLiveData<List<Competition>>()
    private val testDataFetchErrorObservable = PublishSubject.create<Unit>()

    private val testDisplayingCompetition = createDefaultCompetition(
        listOf(
            createDefaultCompetitor(
                homeAway = Competitor.HomeAway.HOME,
                score = "1",
                team = createTeam("H", "Home Long Display Name", "Home Name")
            ),
            createDefaultCompetitor(
                homeAway = Competitor.HomeAway.AWAY,
                score = "2",
                team = createTeam("A", "Away Long Display Name", "Away Name")
            )
        ), status = createStatus(createStatusType(shortDetail = "Short Detail"))
    )

    @Before
    fun setUp() {
        `when`(app.viewModelFactory.create(FeedViewModel::class.java)).thenReturn(mockFeedViewModel)
        `when`(mockFeedViewModel.competitions).thenReturn(competitions)
        `when`(mockFeedViewModel.dataFetchErrorObservable).thenReturn(testDataFetchErrorObservable)
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
    fun `the loading view is initially visible`() {
        onView(withId(R.id.loading)).check(matches(isDisplayed()))
    }

    @Test
    fun `recyclerView is populated when data is available`() {
        competitions.postValue(listOf(createDefaultCompetition()))

        feedFragmentScenario.onFragment {
            assertEquals(1, it.recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun `after data is populated the loading view is not visible`() {
        competitions.postValue(listOf(createDefaultCompetition()))

        onView(withId(R.id.loading)).check(matches(not(isDisplayed())))
    }

    @Test
    fun `a toast is shown when the data fetch is unsuccessful`() {
        ShadowToast.reset()

        testDataFetchErrorObservable.onNext(Unit)

        assertTrue(ShadowToast.showedToast(app.getString(R.string.data_fetch_error)))
    }

    @Test
    fun `left abbreviation is correct`() {
        competitions.postValue(listOf(testDisplayingCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.recyclerView[0].firstAbbr.visibility)
            assertEquals(testDisplayingCompetition.getFirstTeam().team.abbreviation, it.recyclerView[0].firstAbbr.text)
        }
    }

    @Test
    fun `left score is correct`() {
        competitions.postValue(listOf(testDisplayingCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.recyclerView[0].firstScore.visibility)
            assertEquals(testDisplayingCompetition.getFirstTeam().score, it.recyclerView[0].firstScore.text)
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
            assertEquals(View.VISIBLE, it.recyclerView[0].secondAbbr.visibility)
            assertEquals(testDisplayingCompetition.getSecondTeam().team.abbreviation, it.recyclerView[0].secondAbbr.text)
        }
    }

    @Test
    fun `right score is correct`() {
        competitions.postValue(listOf(testDisplayingCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.VISIBLE, it.recyclerView[0].secondScore.visibility)
            assertEquals(testDisplayingCompetition.getSecondTeam().score, it.recyclerView[0].secondScore.text)
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
                createDefaultCompetition(uid = "1"),
                createDefaultCompetition(uid = "2")
            )
        )

        feedFragmentScenario.onFragment {
            assertEquals(2, it.recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun `multiple posts with the same id result in only one displayed`() {
        val competition = createDefaultCompetition(uid = "1")

        competitions.postValue(listOf(competition))
        competitions.postValue(listOf(competition))

        feedFragmentScenario.onFragment {
            assertEquals(1, it.recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun `multiple posts with the different id result in both displayed`() {
        val firstCompetition = createDefaultCompetition(uid = "1")
        val secondCompetition = createDefaultCompetition(uid = "2")

        competitions.postValue(listOf(firstCompetition))
        competitions.postValue(listOf(firstCompetition, secondCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(2, it.recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun `multiple competitions displayed in order`() {

        val inCompetition = createDefaultCompetition(
            status = createStatus(
                createStatusType(
                    shortDetail = "IN",
                    state = Status.Type.State.IN
                )
            )
        )
        val postCompetition = createDefaultCompetition(
            status = createStatus(
                createStatusType(
                    shortDetail = "POST",
                    state = Status.Type.State.POST
                )
            )
        )
        val preCompetition = createDefaultCompetition(
            status = createStatus(
                createStatusType(
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
        val postCompetition = createDefaultCompetition(
            status = createStatus(
                createStatusType(
                    state = Status.Type.State.PRE
                )
            )
        )

        competitions.postValue(listOf(postCompetition))

        feedFragmentScenario.onFragment {
            assertEquals(View.INVISIBLE, it.recyclerView[0].firstScore.visibility)
            assertEquals(View.INVISIBLE, it.recyclerView[0].divider.visibility)
            assertEquals(View.INVISIBLE, it.recyclerView[0].secondScore.visibility)
        }
    }

    @Test
    fun `single competition triggers on click`() {
        competitions.postValue(listOf(createDefaultCompetition(uid = "1")))

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

        val preCompetition = createDefaultCompetition(
            uid = "1",
            status = createStatus(createStatusType(state = Status.Type.State.PRE))
        )

        val postCompetition = createDefaultCompetition(
            uid = "2",
            status = createStatus(createStatusType(state = Status.Type.State.POST))
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