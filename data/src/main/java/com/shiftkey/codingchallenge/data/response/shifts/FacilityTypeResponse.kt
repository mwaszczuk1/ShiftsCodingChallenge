package com.shiftkey.codingchallenge.data.response.shifts

import com.google.gson.annotations.SerializedName

data class FacilityTypeResponse(
    @SerializedName("color")
    val color: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
