package com.alexvanyo.sportsfeed.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Headline(
    @SerializedName("description")
    val description: String
) : Parcelable