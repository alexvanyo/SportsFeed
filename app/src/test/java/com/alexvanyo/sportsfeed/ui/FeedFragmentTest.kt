package com.alexvanyo.sportsfeed.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.TestSportsFeedApp
import com.alexvanyo.sportsfeed.api.ScoreboardData
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.feed_fragment.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestSportsFeedApp::class)
class FeedFragmentTest {

    private val mlbGamesObservable: PublishSubject<ScoreboardData> = PublishSubject.create()
    private lateinit var feedFragmentScenario: FragmentScenario<FeedFragment>

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<TestSportsFeedApp>()
        `when`(app.espnService.getMLBGames()).thenReturn(mlbGamesObservable)
        feedFragmentScenario = launchFragmentInContainer<FeedFragment>()
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
}