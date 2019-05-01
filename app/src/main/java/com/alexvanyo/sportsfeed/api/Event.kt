package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

/**
 * Data class that corresponds to a single event. This usually refers to a single game between two teams, with
 * information about the game status and the teams playing.
 */
data class Event (
    @SerializedName("name")
    val name: String
)
