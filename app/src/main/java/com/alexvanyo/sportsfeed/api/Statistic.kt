package com.alexvanyo.sportsfeed.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Statistic(
    @SerializedName("abbreviation")
    val abbreviation: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("displayValue")
    val displayValue: String
) : Parcelable