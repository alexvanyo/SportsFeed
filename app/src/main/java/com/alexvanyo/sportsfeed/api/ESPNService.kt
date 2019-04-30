package com.alexvanyo.sportsfeed.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ESPNService {
    @GET("sports/baseball/mlb/scoreboard")
    fun getMLBGames(): Observable<ScoreboardData>
}