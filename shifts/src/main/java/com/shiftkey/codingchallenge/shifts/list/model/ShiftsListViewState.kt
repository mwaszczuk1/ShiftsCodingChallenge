package com.shiftkey.codingchallenge.shifts.list.model

import com.shiftkey.codingchallenge.domain.model.shift.ShiftsList
import java.time.LocalDate

data class ShiftsViewState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isUpdateError: Boolean = false,
    val scrollToDate: LocalDate? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val shifts: ShiftsList? = null
)
