package com.alexvanyo.sportsfeed.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    @SerializedName("abbreviation")
    val abbreviation: String,

    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("logo")
    val logo: String,

    @SerializedName("shortDisplayName")
    val shortDisplayName: String
) : Parcelable