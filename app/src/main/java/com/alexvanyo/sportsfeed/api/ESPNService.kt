package com.alexvanyo.sportsfeed.api

import retrofit2.Call
import retrofit2.http.GET

interface ESPNService {
    @GET("sports/baseball/mlb/scoreboard")
    fun getMLBGames(): Call<ScoreboardData>
}