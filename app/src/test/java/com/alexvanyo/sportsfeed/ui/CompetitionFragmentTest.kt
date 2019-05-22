package com.alexvanyo.sportsfeed.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.TestSportsFeedApp
import com.alexvanyo.sportsfeed.api.BaseballCompetitor
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.Competitor
import com.alexvanyo.sportsfeed.api.Status
import com.alexvanyo.sportsfeed.util.mock
import com.alexvanyo.sportsfeed.viewmodel.FeedViewModel
import createBaseballCompetition
import createBaseballCompetitor
import createDefaultCompetition
import createDefaultCompetitor
import createStatus
import createStatusType
import createTeam
import io.reactivex.subjects.PublishSubject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(application = TestSportsFeedApp::class)
class CompetitionFragmentTest {
    private val mockFeedViewModel: FeedViewModel = mock()

    private val app = ApplicationProvider.getApplicationContext<TestSportsFeedApp>()
    private lateinit var competitionFragmentScenario: FragmentScenario<CompetitionFragment>

    private val competition = MutableLiveData<Competition>()
    private val testDataFetchErrorObservable = PublishSubject.create<Unit>()

    private val testDisplayingCompetition = createDefaultCompetition(
        listOf(
            createDefaultCompetitor(
                homeAway = Competitor.HomeAway.HOME,
                score = "1",
                team = createTeam(abbreviation = "H", shortDisplayName = "Home Name")
            ),
            createDefaultCompetitor(
                homeAway = Competitor.HomeAway.AWAY,
                score = "2",
                team = createTeam(abbreviation = "A", shortDisplayName = "Away Name")
            )
        ), status = createStatus(createStatusType(detail = "Very Long Detail"))
    )

    @Before
    fun setUp() {

        `when`(app.viewModelFactory.create(FeedViewModel::class.java)).thenReturn(mockFeedViewModel)
        `when`(mockFeedViewModel.selectedCompetition).thenReturn(competition)
        `when`(mockFeedViewModel.dataFetchErrorObservable).thenReturn(testDataFetchErrorObservable)
        competitionFragmentScenario = launchFragmentInContainer<CompetitionFragment>()
    }

    @Test
    fun `left name is correct`() {
        competition.postValue(testDisplayingCompetition)

        onView(withId(R.id.firstName)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.firstName)).check(matches(withText("Away Name")))
    }

    @Test
    fun `left score is correct`() {
        competition.postValue(testDisplayingCompetition)

        onView(withId(R.id.firstScore)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.firstScore)).check(matches(withText("2")))
    }

    @Test
    fun `divider is visible`() {
        competition.postValue(testDisplayingCompetition)

        onView(withId(R.id.divider)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun `right name is correct`() {
        competition.postValue(testDisplayingCompetition)

        onView(withId(R.id.secondName)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.secondName)).check(matches(withText("Home Name")))
    }

    @Test
    fun `right score is correct`() {
        competition.postValue(testDisplayingCompetition)

        onView(withId(R.id.secondScore)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.secondScore)).check(matches(withText("1")))
    }

    @Test
    fun `status is correct`() {
        competition.postValue(testDisplayingCompetition)

        onView(withId(R.id.status)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.status)).check(matches(withText("Very Long Detail")))
    }

    @Test
    fun `scheduled events hide the score views`() {
        val postCompetition = createDefaultCompetition(
            status = createStatus(
                createStatusType(
                    state = Status.Type.State.PRE
                )
            )
        )

        competition.postValue(postCompetition)

        onView(withId(R.id.firstScore)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.divider)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.secondScore)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun `baseball competition displays box score`() {
        val baseballCompetition = createBaseballCompetition(
            competitors = listOf(
                createBaseballCompetitor(
                    errors = 10,
                    hits = 11,
                    homeAway = Competitor.HomeAway.HOME,
                    linescores = listOf(
                        BaseballCompetitor.LineScoreValue(1),
                        BaseballCompetitor.LineScoreValue(2),
                        BaseballCompetitor.LineScoreValue(3)
                    ),
                    score = "12",
                    team = createTeam(abbreviation = "HOME")
                ),
                createBaseballCompetitor(
                    errors = 13,
                    hits = 14,
                    homeAway = Competitor.HomeAway.AWAY,
                    linescores = listOf(
                        BaseballCompetitor.LineScoreValue(1),
                        BaseballCompetitor.LineScoreValue(2),
                        BaseballCompetitor.LineScoreValue(3)
                    ),
                    score = "15",
                    team = createTeam(abbreviation = "AWAY")
                )
            )
        )

        competition.postValue(baseballCompetition)

        onView(withId(R.id.awayTeamAbbr)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.awayTeamAbbr)).check(matches(withText("AWAY")))

        onView(withId(R.id.awayTeamRuns)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.awayTeamRuns)).check(matches(withText("15")))

        onView(withId(R.id.awayTeamHits)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.awayTeamHits)).check(matches(withText("14")))

        onView(withId(R.id.awayTeamErrors)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.awayTeamErrors)).check(matches(withText("13")))

        onView(withId(R.id.homeTeamAbbr)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.homeTeamAbbr)).check(matches(withText("HOME")))

        onView(withId(R.id.homeTeamRuns)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.homeTeamRuns)).check(matches(withText("12")))

        onView(withId(R.id.homeTeamHits)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.homeTeamHits)).check(matches(withText("11")))

        onView(withId(R.id.homeTeamErrors)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.homeTeamErrors)).check(matches(withText("10")))

        onView(withId(R.id.inningRow)).check(matches(hasChildCount(9)))
        onView(withId(R.id.awayTeamRow)).check(matches(hasChildCount(9)))
        onView(withId(R.id.homeTeamRow)).check(matches(hasChildCount(9)))
    }

    @Test
    fun `extra inning baseball competition has more innings`() {
        val baseballCompetition = createBaseballCompetition(
            competitors = listOf(
                createBaseballCompetitor(
                    errors = 10,
                    hits = 11,
                    homeAway = Competitor.HomeAway.HOME,
                    linescores = listOf(
                        BaseballCompetitor.LineScoreValue(1),
                        BaseballCompetitor.LineScoreValue(2),
                        BaseballCompetitor.LineScoreValue(3),
                        BaseballCompetitor.LineScoreValue(4),
                        BaseballCompetitor.LineScoreValue(5),
                        BaseballCompetitor.LineScoreValue(6),
                        BaseballCompetitor.LineScoreValue(7),
                        BaseballCompetitor.LineScoreValue(8),
                        BaseballCompetitor.LineScoreValue(9),
                        BaseballCompetitor.LineScoreValue(10),
                        BaseballCompetitor.LineScoreValue(11),
                        BaseballCompetitor.LineScoreValue(12)
                    ),
                    score = "12",
                    team = createTeam(abbreviation = "HOME")
                ),
                createBaseballCompetitor(
                    errors = 13,
                    hits = 14,
                    homeAway = Competitor.HomeAway.AWAY,
                    linescores = listOf(
                        BaseballCompetitor.LineScoreValue(1),
                        BaseballCompetitor.LineScoreValue(2),
                        BaseballCompetitor.LineScoreValue(3),
                        BaseballCompetitor.LineScoreValue(4),
                        BaseballCompetitor.LineScoreValue(5),
                        BaseballCompetitor.LineScoreValue(6),
                        BaseballCompetitor.LineScoreValue(7),
                        BaseballCompetitor.LineScoreValue(8),
                        BaseballCompetitor.LineScoreValue(9),
                        BaseballCompetitor.LineScoreValue(10),
                        BaseballCompetitor.LineScoreValue(11),
                        BaseballCompetitor.LineScoreValue(12)
                    ),
                    score = "15",
                    team = createTeam(abbreviation = "AWAY")
                )
            )
        )

        competition.postValue(baseballCompetition)

        onView(withId(R.id.inningRow)).check(matches(hasChildCount(12)))
        onView(withId(R.id.awayTeamRow)).check(matches(hasChildCount(12)))
        onView(withId(R.id.homeTeamRow)).check(matches(hasChildCount(12)))
    }

    @Test
    fun `a toast is shown when the data fetch is unsuccessful`() {
        ShadowToast.reset()

        testDataFetchErrorObservable.onNext(Unit)

        Assert.assertTrue(ShadowToast.showedToast(app.getString(R.string.data_fetch_error)))
    }
}