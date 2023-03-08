package com.shiftkey.codingchallenge.shifts.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.useCase.GetShiftsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ShiftsListViewModel @Inject constructor(
    private val getShiftsListUseCase: GetShiftsListUseCase
) : ViewModel() {

    val state = flow {
        emit(getShiftsListUseCase.invoke("Dallas, TX", LocalDateTime.now().withHour(0).withMinute(0).withSecond(0), LocalDateTime.now().plusDays(7)))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewState.Loading)
}
