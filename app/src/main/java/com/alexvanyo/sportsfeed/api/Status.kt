package com.alexvanyo.sportsfeed.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Status(
    @SerializedName("type")
    val type: Type
) : Parcelable {
    @Parcelize
    data class Type(
        @SerializedName("completed")
        val completed: Boolean,

        @SerializedName("detail")
        val detail: String,

        @SerializedName("shortDetail")
        val shortDetail: String,

        @SerializedName("state")
        val state: State
    ) : Parcelable {
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