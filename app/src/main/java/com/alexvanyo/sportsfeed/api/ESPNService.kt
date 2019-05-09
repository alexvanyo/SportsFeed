package com.alexvanyo.sportsfeed.api

import com.alexvanyo.sportsfeed.api.baseball.BaseballScoreboardData
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Retrofit builder for the ESPN API
 */
interface ESPNService {
    @GET("sports/baseball/mlb/scoreboard")
    fun getMLBGames(): Observable<BaseballScoreboardData>

    @GET("sports/soccer/usa.1/scoreboard")
    fun getMLSGames(): Observable<DefaultScoreboardData>
}