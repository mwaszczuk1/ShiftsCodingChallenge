package com.shiftkey.codingchallenge.data.response.shifts

import com.google.gson.annotations.SerializedName

data class BaseShiftsResponse(
    @SerializedName("data")
    val data: List<ShiftsListResponse>,
    @SerializedName("meta")
    val meta: ShiftsMetaResponse
)
