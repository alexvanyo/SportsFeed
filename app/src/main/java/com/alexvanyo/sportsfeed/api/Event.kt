package com.alexvanyo.sportsfeed.api

/**
 * Interface that corresponds to a single event, which may contain multiple competitions.
 */
interface Event {
    val competitions: List<Competition>
    val name: String
}
