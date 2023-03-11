package com.shiftkey.codingchallenge.shifts.list.model

import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.model.shift.ShiftsList
import com.shiftkey.codingchallenge.domain.viewmodel.reducer
import java.time.LocalDate

object GetShiftsList {

    val dataReducer = reducer<ShiftsViewState, ViewState<ShiftsList>> {
        copy(
            isLoading = it.isLoading(),
            isError = it.isError() && shifts == null,
            errorMessage = (it as? ViewState.Error)?.errorMessage ?: "",
            isUpdateError = (it.isError() && shifts != null),
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

    val screenActionsReducer = reducer<ShiftsViewState, ShiftsListAction> {
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

    sealed class ShiftsListAction {
        class ScrollToDay(val date: LocalDate) : ShiftsListAction()
        class SelectDate(val date: LocalDate) : ShiftsListAction()
        object None : ShiftsListAction()
    }
}
