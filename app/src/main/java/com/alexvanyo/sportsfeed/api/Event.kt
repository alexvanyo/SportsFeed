package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

/**
 * Data class that corresponds to a single event. This usually contains a single game between two teams, with
 * information about the game status and the teams playing.
 */
data class Event (
    @SerializedName("name")
    val name: String,

    @SerializedName("uid")
    val uid: String,

    @SerializedName("competitions")
    val competitions: List<Competition>
)
