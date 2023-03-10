package com.shiftkey.codingchallenge.shifts.details

import androidx.lifecycle.ViewModel
import com.shiftkey.codingchallenge.domain.useCase.GetShiftDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShiftDetailsViewModel @Inject constructor(
    private val getShiftDetailsUseCase: GetShiftDetailsUseCase
) : ViewModel() {

    val state = getShiftDetailsUseCase.invoke()
}
