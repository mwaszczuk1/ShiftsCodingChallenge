package com.shiftkey.codingchallenge.shifts.list.model

import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.model.shift.Shift
import com.shiftkey.codingchallenge.domain.model.shift.ShiftsList
import com.shiftkey.codingchallenge.domain.viewmodel.reducer
import com.shiftkey.codingchallenge.shifts.list.model.ShiftListItem.Shift.Companion.toUi
import java.time.LocalDate

object GetShiftsList {

    val dataReducer = reducer<ShiftsViewState, ViewState<ShiftsList>> { data ->
        val days = calendarDays.plus((data as? ViewState.Success)?.data?.calendarDays ?: emptyList())
        copy(
            isLoading = data.isLoading(),
            isError = data.isError() && shifts == null,
            errorMessage = (data as? ViewState.Error)?.errorMessage ?: "",
            isUpdateError = (data.isError() && shifts != null),
            calendarDays = days.distinct(),
            lat = (data as? ViewState.Success)?.data?.lat ?: lat,
            lng = (data as? ViewState.Success)?.data?.lng ?: lng,
            shifts = (data as? ViewState.Success)?.data?.let {
                buildList {
                    this@reducer.shifts?.let { currentShifts ->
                        addAll(currentShifts)
                    }
                    addAll(it.shifts.flattenShifts(days))
                }.distinct()
            } ?: shifts
        )
    }

    private fun Map<LocalDate, List<Shift>>.flattenShifts(calendarDays: List<LocalDate>) =
        calendarDays.associateWith { date ->
            (get(date) ?: emptyList())
        }.flatMap {
            buildList {
                add(ShiftListItem.Header(it.key))
                addAll(it.value.map {
                    it.toUi()
                })
            }
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
