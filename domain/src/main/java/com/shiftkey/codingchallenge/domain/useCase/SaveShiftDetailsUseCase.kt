package com.shiftkey.codingchallenge.domain.useCase

import com.shiftkey.codingchallenge.domain.model.shift.Shift
import javax.inject.Inject

class SaveShiftDetailsUseCase @Inject constructor(
    private val savedState: SavedShiftDetailsState
) {
    suspend fun invoke(shift: Shift, lat: Double, lng: Double, location: String) {
        savedState.details.value = ShiftDetails(
            shift = shift,
            lat = lat,
            lng = lng,
            location = location
        )
    }
}
