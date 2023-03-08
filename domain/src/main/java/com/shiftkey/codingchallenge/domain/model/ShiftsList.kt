package com.shiftkey.codingchallenge.domain.model

import com.shiftkey.codingchallenge.data.response.shifts.BaseShiftsResponse
import java.time.LocalDate

data class ShiftsList(
    val lat: Double,
    val lng: Double,
    val shifts: Map<LocalDate, List<Shift>>
)

fun BaseShiftsResponse.toDomain() = ShiftsList(
    lat = meta.lat,
    lng = meta.lng,
    shifts = data.associate {
        it.date to it.shifts.map { shift -> shift.toDomain() }
    }
)
