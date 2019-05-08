package com.alexvanyo.sportsfeed.viewmodel

import TestUtil
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.util.TestSchedulerRule
import com.alexvanyo.sportsfeed.util.mock
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FeedViewModelTest {

    @get:Rule
    val testSchedulerRule = TestSchedulerRule()

    private val mockEspnService: ESPNService = mock()
    private val mockObserver: Observer<List<Competition>> = mock()
    private val feedViewModel = FeedViewModel(mockEspnService)
    private val testScoreboardData = TestUtil.createScoreboardData(listOf(TestUtil.createEvent()))

    @Test
    fun `data is updated on first subscribe`() {
        `when`(mockEspnService.getMLBGames()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.competitions.observeForever(mockObserver)

        testSchedulerRule.testScheduler.triggerActions()

        verify(mockObserver).onChanged(testScoreboardData.events.flatMap { it.competitions })
    }

    @Test
    fun `data isn't updated on error`() {
        `when`(mockEspnService.getMLBGames()).thenReturn(Observable.error(IOException()))

        feedViewModel.competitions.observeForever(mockObserver)

        testSchedulerRule.testScheduler.triggerActions()

        verify(mockObserver, never()).onChanged(any())
    }
}