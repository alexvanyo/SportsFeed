package com.alexvanyo.sportsfeed.api

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Retrofit builder for the ESPN API
 */
interface ESPNService {
    @GET("sports/baseball/mlb/scoreboard?dates=20190101-20200101&limit=100")
    fun getMLBGames(): Observable<ScoreboardData>
}