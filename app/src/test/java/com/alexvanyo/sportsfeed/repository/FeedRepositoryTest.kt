package com.alexvanyo.sportsfeed.repository

import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.util.mock
import io.reactivex.Observable
import org.junit.Test
import org.mockito.Mockito.`when`
import java.io.IOException

class FeedRepositoryTest {

    private val mockESPNService: ESPNService = mock()

    private val feedRepository = FeedRepository(mockESPNService)

    @Test
    fun `error isn't propagated on MLB data error`() {
        `when`(mockESPNService.getMLBGames()).thenReturn(Observable.error(IOException()))
        `when`(mockESPNService.getMLSGames()).thenReturn(Observable.just(TestUtil.createDefaultScoreboardData()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(1)
    }

    @Test
    fun `error isn't propagated on MLS data error`() {
        `when`(mockESPNService.getMLBGames()).thenReturn(Observable.just(TestUtil.createBaseballScoreboardData()))
        `when`(mockESPNService.getMLSGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(1)
    }

    @Test
    fun `error isn't propagated on either data error`() {
        `when`(mockESPNService.getMLBGames()).thenReturn(Observable.error(IOException()))
        `when`(mockESPNService.getMLSGames()).thenReturn(Observable.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(0)
    }
}