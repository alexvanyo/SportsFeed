package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

/**
 * Data class that corresponds to a single event, which may contain multiple competitions.
 */
data class Event(
    @SerializedName("name")
    val name: String,

    @SerializedName("competitions")
    val competitions: List<Competition>
)
