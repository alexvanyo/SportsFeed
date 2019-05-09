package com.alexvanyo.sportsfeed.repository

import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.util.mock
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import java.io.IOException

class FeedRepositoryTest {

    private val mockESPNService: ESPNService = mock()

    private val feedRepository = FeedRepository(mockESPNService)

    @Before
    fun setUp() {
        `when`(mockESPNService.getMLBGames()).thenReturn(Observable.just(TestUtil.createBaseballScoreboardData()))
        `when`(mockESPNService.getMLSGames()).thenReturn(Observable.just(TestUtil.createSoccerScoreboardData()))
        `when`(mockESPNService.getNHLGames()).thenReturn(Observable.just(TestUtil.createDefaultScoreboardData()))
        `when`(mockESPNService.getNBAGames()).thenReturn(Observable.just(TestUtil.createDefaultScoreboardData()))
    }

    @Test
    fun `error isn't propagated on MLB data error`() {
        `when`(mockESPNService.getMLBGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
    }

    @Test
    fun `error isn't propagated on MLS data error`() {
        `when`(mockESPNService.getMLSGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
    }

    @Test
    fun `error isn't propagated on NHL data error`() {
        `when`(mockESPNService.getNHLGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
    }

    @Test
    fun `error isn't propagated on NBA data error`() {
        `when`(mockESPNService.getNBAGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
    }

    @Test
    fun `error isn't propagated on any data error`() {
        `when`(mockESPNService.getMLBGames()).thenReturn(Observable.error(IOException()))
        `when`(mockESPNService.getMLSGames()).thenReturn(Observable.error(IOException()))
        `when`(mockESPNService.getNHLGames()).thenReturn(Observable.error(IOException()))
        `when`(mockESPNService.getNBAGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(0)
    }
}