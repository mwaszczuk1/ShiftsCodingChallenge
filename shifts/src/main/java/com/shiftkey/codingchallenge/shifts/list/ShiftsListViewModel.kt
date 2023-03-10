package com.shiftkey.codingchallenge.shifts.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.model.Shift
import com.shiftkey.codingchallenge.domain.model.ShiftsList
import com.shiftkey.codingchallenge.domain.useCase.GetShiftsListUseCase
import com.shiftkey.codingchallenge.domain.useCase.SaveShiftDetailsUseCase
import com.shiftkey.codingchallenge.domain.viewmodel.reducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ShiftsListViewModel @Inject constructor(
    private val getShiftsListUseCase: GetShiftsListUseCase,
    private val saveShiftDetailsUseCase: SaveShiftDetailsUseCase
) : ViewModel() {

    private val actions = MutableStateFlow<ShiftsListAction>(ShiftsListAction.None)
    private val params = MutableStateFlow(GetShiftsListUseCase.Params())
    private val data = callbackFlow {
        params.collect { params ->
            send(ViewState.Updating)
            send(
                getShiftsListUseCase.invoke(params)
            )
        }
    }

    private val dataReducer = reducer<ShiftsViewState, ViewState<ShiftsList>> {
        copy(
            isLoading = false,
            isUpdating = it.isUpdating(),
            isError = isError && it.isError(),
            isUpdateError = isError && it.isError(),
            shifts = (it as? ViewState.Success)?.data?.let {
                ShiftsList(
                    lat = it.lat,
                    lng = it.lng,
                    shifts = buildMap {
                        shifts?.shifts?.let { currentShifts ->
                            putAll(currentShifts)
                        }
                        putAll(it.shifts)
                    }
                )
            } ?: shifts
        )
    }

    private val backingStateReducer = reducer<ShiftsViewState, ShiftsListAction> {
        when (it) {
            is ShiftsListAction.ScrollToDay -> {
                copy(
                    scrollToDate = it.date
                )
            }
            is ShiftsListAction.SelectDate -> {
                copy(
                    selectedDate = it.date
                )
            }
            else -> this
        }
    }
    private val reducers = listOf(dataReducer, backingStateReducer)

    val state = merge(data, actions).scan(ShiftsViewState()) { acc, value ->
        reducers.fold(acc) { accState, reduce ->
            reduce(
                accState,
                value
            )
        }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ShiftsViewState())

    fun loadNextWeek() {
        if (!state.value.isUpdating) {
            params.value = params.value.copy(
                startDateTime = params.value.startDateTime.plusDays(7),
                endDateTime = params.value.endDateTime.plusDays(7)
            )
        }
    }

    fun scrollToDay(day: LocalDate) {
        actions.value = ShiftsListAction.ScrollToDay(day)
    }

    fun selectDate(date: LocalDate) {
        actions.value = ShiftsListAction.SelectDate(date)
    }

    fun saveShiftDetails(shift: Shift) {
        viewModelScope.launch {
            saveShiftDetailsUseCase.invoke(
                shift,
                state.value.shifts?.lat ?: 0.0,
                state.value.shifts?.lng ?: 0.0,
                params.value.address,
            )
        }
    }
}

data class ShiftsViewState(
    val isLoading: Boolean = true,
    val isUpdating: Boolean = false,
    val isError: Boolean = false,
    val isUpdateError: Boolean = false,
    val scrollToDate: LocalDate? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val shifts: ShiftsList? = null
)

sealed class ShiftsListAction {
    class ScrollToDay(val date: LocalDate) : ShiftsListAction()
    class SelectDate(val date: LocalDate) : ShiftsListAction()
    object None : ShiftsListAction()
}
