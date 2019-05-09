package com.alexvanyo.sportsfeed.api

/**
 * Interface for the main object returned by an ESPN scoreboard API request
 */
interface ScoreboardData {
    val events: List<Event>
}
