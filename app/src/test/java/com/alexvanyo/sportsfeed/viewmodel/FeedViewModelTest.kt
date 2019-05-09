package com.alexvanyo.sportsfeed.viewmodel

import TestUtil
import androidx.lifecycle.Observer
import com.alexvanyo.sportsfeed.api.Competition
import com.alexvanyo.sportsfeed.repository.FeedRepository
import com.alexvanyo.sportsfeed.util.PausableObservable
import com.alexvanyo.sportsfeed.util.TestSchedulerRule
import com.alexvanyo.sportsfeed.util.mock
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.rules.TestRule

class FeedViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = TestSchedulerRule()

    private val mockFeedRepository: FeedRepository = mock()
    private val mockPausableObservable: PausableObservable<Unit> = mock()
    private val mockCompetitionListObserver: Observer<List<Competition>> = mock()
    private val mockSelectedCompetitionObserver: Observer<Competition> = mock()

    private val testNotifier = PublishSubject.create<Unit>()
    private val testScoreboardData = TestUtil.createDefaultScoreboardData(listOf(TestUtil.createDefaultEvent()))

    private lateinit var feedViewModel: FeedViewModel

    @Before
    fun setUp() {
        `when`(mockPausableObservable.observable).thenReturn(testNotifier)

        feedViewModel = FeedViewModel(mockFeedRepository, mockPausableObservable)
    }

    @Test
    fun `pausable observable is resumed on competition list observe`() {
        `when`(mockFeedRepository.getScoreboardData()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.competitions.observeForever(mockCompetitionListObserver)

        verify(mockPausableObservable).resume()
    }

    @Test
    fun `pausable observable is paused on competition list release`() {
        `when`(mockFeedRepository.getScoreboardData()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.competitions.observeForever(mockCompetitionListObserver)

        feedViewModel.competitions.removeObserver(mockCompetitionListObserver)

        verify(mockPausableObservable).pause()
    }

    @Test
    fun `pausable observable is resumed on selected competition observe`() {
        `when`(mockFeedRepository.getScoreboardData()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.selectedCompetition.observeForever(mockSelectedCompetitionObserver)

        verify(mockPausableObservable).resume()
    }

    @Test
    fun `pausable observable is paused on selected competition release`() {
        `when`(mockFeedRepository.getScoreboardData()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.selectedCompetition.observeForever(mockSelectedCompetitionObserver)

        feedViewModel.selectedCompetition.removeObserver(mockSelectedCompetitionObserver)

        verify(mockPausableObservable).pause()
    }

    @Test
    fun `pausable observable is not paused on competition list release with selected competition active`() {
        `when`(mockFeedRepository.getScoreboardData()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.competitions.observeForever(mockCompetitionListObserver)
        feedViewModel.selectedCompetition.observeForever(mockSelectedCompetitionObserver)

        feedViewModel.competitions.removeObserver(mockCompetitionListObserver)

        verify(mockPausableObservable, never()).pause()
    }

    @Test
    fun `pausable observable is not paused on selected competition release with competition list active`() {
        `when`(mockFeedRepository.getScoreboardData()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.competitions.observeForever(mockCompetitionListObserver)
        feedViewModel.selectedCompetition.observeForever(mockSelectedCompetitionObserver)

        feedViewModel.selectedCompetition.removeObserver(mockSelectedCompetitionObserver)

        verify(mockPausableObservable, never()).pause()
    }

    @Test
    fun `pausable observable is paused when both released`() {
        `when`(mockFeedRepository.getScoreboardData()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.competitions.observeForever(mockCompetitionListObserver)
        feedViewModel.selectedCompetition.observeForever(mockSelectedCompetitionObserver)

        feedViewModel.competitions.removeObserver(mockCompetitionListObserver)
        feedViewModel.selectedCompetition.removeObserver(mockSelectedCompetitionObserver)

        verify(mockPausableObservable).pause()
    }

    @Test
    fun `data is updated when a request is made`() {
        `when`(mockFeedRepository.getScoreboardData()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.competitions.observeForever(mockCompetitionListObserver)

        testSchedulerRule.testScheduler.triggerActions()
        testNotifier.onNext(Unit)
        testSchedulerRule.testScheduler.triggerActions()

        verify(mockCompetitionListObserver).onChanged(testScoreboardData.events.flatMap { it.competitions })
    }

    @Test
    fun `select competition for a competition that exists`() {
        `when`(mockFeedRepository.getScoreboardData()).thenReturn(Observable.just(testScoreboardData))

        feedViewModel.selectedCompetition.observeForever(mockSelectedCompetitionObserver)

        testSchedulerRule.testScheduler.triggerActions()
        testNotifier.onNext(Unit)
        testSchedulerRule.testScheduler.triggerActions()

        feedViewModel.selectCompetition(testScoreboardData.events[0].competitions[0].uid)

        verify(mockSelectedCompetitionObserver).onChanged(testScoreboardData.events[0].competitions[0])
    }
}