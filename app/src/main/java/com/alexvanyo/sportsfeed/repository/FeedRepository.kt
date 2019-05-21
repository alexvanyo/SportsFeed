package com.alexvanyo.sportsfeed.repository

import com.alexvanyo.sportsfeed.api.EspnService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Repository class responsible for making API calls and filtering data.
 */
class FeedRepository @Inject constructor(private val espnService: EspnService) {
    fun getScoreboardData(): Observable<ScoreboardData> {
        return Single.mergeDelayError(
            espnService.getMLBGames(),
            espnService.getMLSGames(),
            espnService.getNHLGames(),
            espnService.getNBAGames()
        ).toObservable()
    }
}