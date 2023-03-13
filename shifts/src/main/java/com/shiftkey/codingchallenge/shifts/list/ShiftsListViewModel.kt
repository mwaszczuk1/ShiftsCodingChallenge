package com.shiftkey.codingchallenge.shifts.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.useCase.GetShiftsListUseCase
import com.shiftkey.codingchallenge.domain.useCase.SaveShiftDetailsUseCase
import com.shiftkey.codingchallenge.shifts.list.model.GetShiftsList.dataReducer
import com.shiftkey.codingchallenge.shifts.list.model.GetShiftsList.screenActionsReducer
import com.shiftkey.codingchallenge.shifts.list.model.GetShiftsList.ShiftsListAction
import com.shiftkey.codingchallenge.shifts.list.model.ShiftListItem
import com.shiftkey.codingchallenge.shifts.list.model.ShiftsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ShiftsListViewModel @Inject constructor(
    private val getShiftsListUseCase: GetShiftsListUseCase,
    private val saveShiftDetailsUseCase: SaveShiftDetailsUseCase
) : ViewModel() {

    private val actions = MutableStateFlow<ShiftsListAction>(ShiftsListAction.None)
    private val requestParams = MutableStateFlow(GetShiftsListUseCase.Params())
    private val data = callbackFlow {
        requestParams.collectLatest { requestParams ->
            send(ViewState.Loading)
            send(
                getShiftsListUseCase.invoke(requestParams)
            )
        }
    }

    private val reducers = listOf(dataReducer, screenActionsReducer)
    val state = merge(data, actions).scan(ShiftsViewState()) { acc, value ->
        reducers.fold(acc) { accState, reduce ->
            reduce(
                accState,
                value
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, ShiftsViewState())

    fun loadNextWeek() {
        if (!state.value.isLoading) {
            requestParams.value = requestParams.value.copy(
                startDateTime = requestParams.value.startDateTime.plusDays(7),
                endDateTime = requestParams.value.endDateTime.plusDays(7)
            )
        }
    }

    fun reload() {
        viewModelScope.launch {
            requestParams.emit(
                requestParams.value.copy(
                    startDateTime = requestParams.value.startDateTime,
                    endDateTime = requestParams.value.endDateTime
                )
            )
        }
    }

    fun scrollToDay(day: LocalDate) {
        actions.value = ShiftsListAction.ScrollToDay(day)
    }

    fun selectDate(date: LocalDate) {
        actions.value = ShiftsListAction.SelectDate(date)
    }

    fun saveShiftDetails(shift: ShiftListItem.Shift) {
        saveShiftDetailsUseCase.invoke(
            shift.toDomain(),
            state.value.lat,
            state.value.lng,
            requestParams.value.address,
        )
    }
}
