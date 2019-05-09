package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

data class Headline(
    @SerializedName("description")
    val description: String
)