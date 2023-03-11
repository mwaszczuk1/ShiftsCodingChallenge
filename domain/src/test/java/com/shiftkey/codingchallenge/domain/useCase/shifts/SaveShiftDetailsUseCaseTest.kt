package com.shiftkey.codingchallenge.domain.useCase.shifts

import com.shiftkey.codingchallenge.domain.useCase.*
import org.junit.Test

class SaveShiftDetailsUseCaseTest {

    private val savedState = SavedShiftDetailsState()
    private val saveShiftDetailsUseCase = SaveShiftDetailsUseCase(savedState)
    private val getShiftDetailsUseCase = GetShiftDetailsUseCase(savedState)

    private val location = "Dallas, TX"

    @Test
    fun `should save and store shift details`() {
        // When
        saveShiftDetailsUseCase.invoke(
            shiftDomain,
            1.0,
            1.0,
            location
        )
        val savedDetails = getShiftDetailsUseCase.invoke()
        // Then
        assert(expectedShiftDetails == savedDetails.value)
    }
}
