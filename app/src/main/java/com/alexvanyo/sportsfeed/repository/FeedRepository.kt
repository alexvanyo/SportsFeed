package com.alexvanyo.sportsfeed.repository

import com.alexvanyo.sportsfeed.api.ESPNService
import com.alexvanyo.sportsfeed.api.ScoreboardData
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Repository class responsible for making API calls and filtering data.
 */
class FeedRepository @Inject constructor(private val espnService: ESPNService) {
    fun getScoreboardData(): Observable<ScoreboardData> {
        return Observable.merge(
            espnService.getMLBGames().onErrorResumeNext(Observable.empty()),
            espnService.getMLSGames().onErrorResumeNext(Observable.empty())
        )
    }
}