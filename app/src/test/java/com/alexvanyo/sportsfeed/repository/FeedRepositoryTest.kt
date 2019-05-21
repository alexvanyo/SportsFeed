package com.alexvanyo.sportsfeed.repository

import com.alexvanyo.sportsfeed.api.EspnService
import com.alexvanyo.sportsfeed.util.mock
import createBaseballScoreboardData
import createDefaultScoreboardData
import createSoccerScoreboardData
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import java.io.IOException

class FeedRepositoryTest {

    private val mockEspnService: EspnService = mock()

    private val feedRepository = FeedRepository(mockEspnService)

    @Before
    fun setUp() {
        `when`(mockEspnService.getMLBGames()).thenReturn(Observable.just(createBaseballScoreboardData()))
        `when`(mockEspnService.getMLSGames()).thenReturn(Observable.just(createSoccerScoreboardData()))
        `when`(mockEspnService.getNHLGames()).thenReturn(Observable.just(createDefaultScoreboardData()))
        `when`(mockEspnService.getNBAGames()).thenReturn(Observable.just(createDefaultScoreboardData()))
    }

    @Test
    fun `error isn't propagated on MLB data error`() {
        `when`(mockEspnService.getMLBGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
    }

    @Test
    fun `error isn't propagated on MLS data error`() {
        `when`(mockEspnService.getMLSGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
    }

    @Test
    fun `error isn't propagated on NHL data error`() {
        `when`(mockEspnService.getNHLGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
    }

    @Test
    fun `error isn't propagated on NBA data error`() {
        `when`(mockEspnService.getNBAGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
    }

    @Test
    fun `error isn't propagated on any data error`() {
        `when`(mockEspnService.getMLBGames()).thenReturn(Observable.error(IOException()))
        `when`(mockEspnService.getMLSGames()).thenReturn(Observable.error(IOException()))
        `when`(mockEspnService.getNHLGames()).thenReturn(Observable.error(IOException()))
        `when`(mockEspnService.getNBAGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(0)
    }
}