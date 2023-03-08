package com.shiftkey.codingchallenge.data.response.shifts

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ShiftsListResponse(
    @SerializedName("date")
    val date: LocalDate,
    @SerializedName("shifts")
    val shifts: List<ShiftResponse>
)
