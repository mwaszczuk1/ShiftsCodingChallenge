package com.shiftkey.codingchallenge.shifts.list.model

import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.model.shift.ShiftsList
import com.shiftkey.codingchallenge.domain.viewmodel.reducer
import java.time.LocalDate

object GetShiftsList {

    val dataReducer = reducer<ShiftsViewState, ViewState<ShiftsList>> { data ->
        copy(
            isLoading = data.isLoading(),
            isError = data.isError() && shifts == null,
            errorMessage = (data as? ViewState.Error)?.errorMessage ?: "",
            isUpdateError = (data.isError() && shifts != null),
            shifts = (data as? ViewState.Success)?.data?.let {
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

    val screenActionsReducer = reducer<ShiftsViewState, ShiftsListAction> { action ->
        when (action) {
            is ShiftsListAction.ScrollToDay -> {
                copy(
                    scrollToDate = action.date
                )
            }
            is ShiftsListAction.SelectDate -> {
                copy(
                    selectedDate = action.date
                )
            }
            else -> this
        }
    }

    sealed class ShiftsListAction {
        class ScrollToDay(val date: LocalDate) : ShiftsListAction()
        class SelectDate(val date: LocalDate) : ShiftsListAction()
        object None : ShiftsListAction()
    }
}
