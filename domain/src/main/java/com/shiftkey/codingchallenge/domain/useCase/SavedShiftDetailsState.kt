package com.shiftkey.codingchallenge.domain.useCase

import com.shiftkey.codingchallenge.domain.model.shift.Shift
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedShiftDetailsState @Inject constructor() {
    val details = MutableStateFlow<ShiftDetails?>(null)
}

data class ShiftDetails(
    val shift: Shift,
    val location: String,
    val lat: Double,
    val lng: Double
)
