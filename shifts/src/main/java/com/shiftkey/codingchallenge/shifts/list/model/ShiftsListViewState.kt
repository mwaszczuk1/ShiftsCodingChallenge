package com.shiftkey.codingchallenge.shifts.list.model

import java.time.LocalDate

data class ShiftsViewState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val calendarDays: List<LocalDate> = emptyList(),
    val errorMessage: String = "",
    val isUpdateError: Boolean = false,
    val scrollToDate: LocalDate? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val shifts: List<ShiftListItem>? = null
)
