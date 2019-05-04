package com.alexvanyo.sportsfeed.api

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("type")
    val type: Type
) {
    data class Type(
        @SerializedName("completed")
        val completed: Boolean,

        @SerializedName("detail")
        val detail: String,

        @SerializedName("state")
        val state: State
    ) {
        enum class State {
            @SerializedName("post")
            POST,
            @SerializedName("in")
            IN,
            @SerializedName("pre")
            PRE,
        }
    }
}