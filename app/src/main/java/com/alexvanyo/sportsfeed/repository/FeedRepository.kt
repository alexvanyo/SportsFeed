package com.alexvanyo.sportsfeed.repository

import com.alexvanyo.sportsfeed.api.EspnService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Repository class responsible for making API calls and filtering data.
 */
class FeedRepository @Inject constructor(private val espnService: EspnService) {
    fun getScoreboardData(): Observable<ScoreboardData> {
        return Observable.merge(
            espnService.getMLBGames().onErrorResumeNext(Observable.empty()),
            espnService.getMLSGames().onErrorResumeNext(Observable.empty()),
            espnService.getNHLGames().onErrorResumeNext(Observable.empty()),
            espnService.getNBAGames().onErrorResumeNext(Observable.empty())
        )
    }
}