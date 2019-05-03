package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

data class Competition (
    @SerializedName("competitors")
    val competitors: List<Competitor>,

    @SerializedName("status")
    val status: Status
)