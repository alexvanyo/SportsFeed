package com.alexvanyo.sportsfeed.api

import com.alexvanyo.sportsfeed.api.Team

interface Competitor {
    val score: String
    val team: Team
}
