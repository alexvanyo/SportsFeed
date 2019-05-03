package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

/**
 * Data class that corresponds to a single event.
 */
data class Event(
    @SerializedName("name")
    val name: String,

    @SerializedName("uid")
    val uid: String,

    @SerializedName("competitions")
    val competitions: List<Competition>
) : Comparable<Event> {
    override fun compareTo(other: Event): Int {
        return this.name.compareTo(other.name)
    }
}
