package com.alexvanyo.sportsfeed.repository

import com.alexvanyo.sportsfeed.api.EspnService
import com.alexvanyo.sportsfeed.util.mock
import createBaseballScoreboardData
import createDefaultScoreboardData
import createSoccerScoreboardData
import io.reactivex.Single
import io.reactivex.exceptions.CompositeException
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import java.io.IOException

class FeedRepositoryTest {

    private val mockEspnService: EspnService = mock()

    private val feedRepository = FeedRepository(mockEspnService)

    @Before
    fun setUp() {
        `when`(mockEspnService.getMLBGames()).thenReturn(Single.just(createBaseballScoreboardData()))
        `when`(mockEspnService.getMLSGames()).thenReturn(Single.just(createSoccerScoreboardData()))
        `when`(mockEspnService.getNHLGames()).thenReturn(Single.just(createDefaultScoreboardData()))
        `when`(mockEspnService.getNBAGames()).thenReturn(Single.just(createDefaultScoreboardData()))
    }

    @Test
    fun `error is propagated on MLB data error with all others returning`() {
        `when`(mockEspnService.getMLBGames()).thenReturn(Single.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
        allData.assertError(IOException::class.java)
    }

    @Test
    fun `error is propagated on MLS data error with all others returning`() {
        `when`(mockEspnService.getMLSGames()).thenReturn(Single.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
        allData.assertError(IOException::class.java)
    }

    @Test
    fun `error is propagated on NHL data error with all others returning`() {
        `when`(mockEspnService.getNHLGames()).thenReturn(Single.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
        allData.assertError(IOException::class.java)
    }

    @Test
    fun `error is propagated on NBA data error with all others returning`() {
        `when`(mockEspnService.getNBAGames()).thenReturn(Single.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(3)
        allData.assertError(IOException::class.java)
    }

    @Test
    fun `error is propogated when all error`() {
        `when`(mockEspnService.getMLBGames()).thenReturn(Single.error(IOException()))
        `when`(mockEspnService.getMLSGames()).thenReturn(Single.error(IOException()))
        `when`(mockEspnService.getNHLGames()).thenReturn(Single.error(IOException()))
        `when`(mockEspnService.getNBAGames()).thenReturn(Single.error(IOException()))

        val allData = feedRepository.getScoreboardData().test()

        allData.assertValueCount(0)
        allData.assertError(CompositeException::class.java)
        allData.assertError { t -> (t as CompositeException).size() == 4 }
    }
}