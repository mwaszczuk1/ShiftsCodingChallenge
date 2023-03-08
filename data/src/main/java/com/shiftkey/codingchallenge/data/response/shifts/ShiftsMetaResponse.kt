package com.shiftkey.codingchallenge.data.response.shifts

import com.google.gson.annotations.SerializedName

data class ShiftsMetaResponse(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)
