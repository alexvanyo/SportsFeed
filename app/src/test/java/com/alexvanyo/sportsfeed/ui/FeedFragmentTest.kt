package com.alexvanyo.sportsfeed.ui

import TestUtil
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.TestSportsFeedApp
import com.alexvanyo.sportsfeed.api.ScoreboardData
import com.alexvanyo.sportsfeed.util.mock
import com.alexvanyo.sportsfeed.viewmodel.FeedViewModel
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
    private val mockFeedViewModel: FeedViewModel = mock()

    private lateinit var feedFragmentScenario: FragmentScenario<FeedFragment>

    private val mlbData = MutableLiveData<ScoreboardData>()

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<TestSportsFeedApp>()
        `when`(app.viewModelFactory.create(FeedViewModel::class.java)).thenReturn(mockFeedViewModel)
        `when`(mockFeedViewModel.mlbData).thenReturn(mlbData)
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

    @Test
    fun `recyclerView is populated when data is available`() {
        mlbData.postValue(
            TestUtil.createScoreboardData(
                listOf(TestUtil.createEvent())
            )
        )

        feedFragmentScenario.onFragment {
            assertEquals(1, it.recyclerView.adapter!!.itemCount)
        }
    }
}