package com.alexvanyo.sportsfeed.api

import com.alexvanyo.sportsfeed.api.baseball.BaseballScoreboardData
import com.alexvanyo.sportsfeed.api.soccer.SoccerScoreboardData
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Retrofit builder for the ESPN API
 */
interface EspnService {
    @GET("sports/baseball/mlb/scoreboard")
    fun getMLBGames(): Single<BaseballScoreboardData>

    @GET("sports/soccer/usa.1/scoreboard")
    fun getMLSGames(): Single<SoccerScoreboardData>

    @GET("sports/hockey/nhl/scoreboard")
    fun getNHLGames(): Single<DefaultScoreboardData>

    @GET("sports/basketball/nba/scoreboard")
    fun getNBAGames(): Single<DefaultScoreboardData>
}