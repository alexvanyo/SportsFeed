package com.alexvanyo.sportsfeed.api

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Retrofit builder for the ESPN API
 */
interface ESPNService {
    @GET("sports/baseball/mlb/scoreboard")
    fun getMLBGames(): Observable<ScoreboardData>
}