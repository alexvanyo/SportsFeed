package com.alexvanyo.sportsfeed.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexvanyo.sportsfeed.DaggerTestAppComponent
import com.alexvanyo.sportsfeed.R
import com.alexvanyo.sportsfeed.TestSportsFeedApp
import com.alexvanyo.sportsfeed.ui.FeedFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestSportsFeedApp::class)
class FeedFragmentTest {

    private lateinit var feedFragmentScenario: FragmentScenario<FeedFragment>

    @Before
    fun setUp() {

        val app = ApplicationProvider.getApplicationContext<TestSportsFeedApp>()
        DaggerTestAppComponent.builder().create(app).inject(app)

        val factory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                val fragment = super.instantiate(classLoader, className)
                app.supportFragmentInjector().inject(fragment)
                return fragment
            }
        }

        feedFragmentScenario = launchFragmentInContainer<FeedFragment>(factory = factory)
    }


    @Test
    fun recyclerView_is_visible() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }
}