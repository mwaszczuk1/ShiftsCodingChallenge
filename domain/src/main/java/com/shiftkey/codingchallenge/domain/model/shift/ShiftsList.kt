package com.shiftkey.codingchallenge.domain.model.shift

import com.shiftkey.codingchallenge.data.response.shifts.BaseShiftsResponse
import java.time.LocalDate

data class ShiftsList(
    val lat: Double,
    val lng: Double,
    val calendarDays: List<LocalDate>,
    val shifts: Map<LocalDate, List<Shift>>
)

fun BaseShiftsResponse.toDomain(calendarDays: List<LocalDate>) = ShiftsList(
    lat = meta.lat,
    lng = meta.lng,
    calendarDays = calendarDays,
    shifts = data.associate {
        it.date to it.shifts.map { shift -> shift.toDomain() }
    }
)
