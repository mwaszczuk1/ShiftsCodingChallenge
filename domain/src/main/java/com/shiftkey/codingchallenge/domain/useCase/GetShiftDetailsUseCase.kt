package com.shiftkey.codingchallenge.domain.useCase

import javax.inject.Inject

class GetShiftDetailsUseCase @Inject constructor(
    private val shiftDetailsState: SavedShiftDetailsState
) {

    fun invoke() = shiftDetailsState.details
}
