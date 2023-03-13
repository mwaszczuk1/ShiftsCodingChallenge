package com.shiftkey.codingchallenge.shifts.list.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shiftkey.codingchallenge.core.formatter.getDateLabel
import com.shiftkey.codingchallenge.design.components.ScreenError
import com.shiftkey.codingchallenge.design.components.WeekCalendar
import com.shiftkey.codingchallenge.design.components.topBar.LocalTopBar
import com.shiftkey.codingchallenge.design.theme.SizeS
import com.shiftkey.codingchallenge.design.theme.SizeXXXS
import com.shiftkey.codingchallenge.shifts.R
import com.shiftkey.codingchallenge.shifts.details.SHIFT_DETAILS_SCREEN_ROUTE
import com.shiftkey.codingchallenge.shifts.list.ShiftsListViewModel
import com.shiftkey.codingchallenge.shifts.list.model.ShiftListItem
import timber.log.Timber
import java.time.LocalDate

@Composable
fun ShiftsListScreen(
    viewModel: ShiftsListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    with(LocalTopBar.current) {
        setTitle(stringResource(R.string.shifts_list_screen_title))
        toggleNavigationIcon(false)
    }

    val state by viewModel.state.collectAsState()

    LocalContext.current.let {
        LaunchedEffect(state.isUpdateError) {
            if (state.isUpdateError) {
                Toast.makeText(it, state.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        state.shifts?.let {
            ShiftsListLayout(
                data = it,
                scrollToDay = state.scrollToDate,
                selectedDate = state.selectedDate,
                calendarDays = state.calendarDays,
                updateCurrentDate = viewModel::selectDate,
                updateScrollToDate = viewModel::scrollToDay,
                loadNextWeek = viewModel::loadNextWeek,
                onItemClicked = { shift ->
                    viewModel.saveShiftDetails(shift)
                    navController.navigate(SHIFT_DETAILS_SCREEN_ROUTE)
                }
            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            visible = state.isLoading
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(bottom = SizeS)
                    .shadow(elevation = SizeXXXS, shape = CircleShape)
                    .background(MaterialTheme.colors.surface, CircleShape)
                    .scale(0.8f)
            )
        }
        if (state.isError) {
            ScreenError(
                errorMessage = state.errorMessage,
            ) {
                viewModel.reload()
            }
        }
    }
}

@Composable
fun ShiftsListLayout(
    data: List<ShiftListItem>,
    calendarDays: List<LocalDate>,
    scrollToDay: LocalDate?,
    selectedDate: LocalDate,
    updateCurrentDate: (LocalDate) -> Unit,
    updateScrollToDate: (LocalDate) -> Unit,
    loadNextWeek: () -> Unit,
    onItemClicked: (ShiftListItem.Shift) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(scrollToDay) {
        scrollToDay?.let { date ->
            val index = data.filterIsInstance<ShiftListItem.Header>().indexOfFirst { it.date == date }
            listState.animateScrollToItem(index)
        }
    }

    val loadMore by remember {
        derivedStateOf {
            val lastItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val itemsListCount = listState.layoutInfo.totalItemsCount
            if (lastItemIndex != null && itemsListCount > 0) {
                lastItemIndex == itemsListCount - 1
            } else {
                false
            }
        }
    }

    val changeCurrentDate by remember {
        derivedStateOf {
            if (listState.isScrollInProgress) {
                Timber.tag("LOL").d("scroll in progress")
                val changeDate = listState.layoutInfo.visibleItemsInfo
                    .firstOrNull().takeIf { it?.key is LocalDate }
                changeDate

            } else {
                null
            }
        }
    }

    (changeCurrentDate?.key as? LocalDate)?.let {
        LaunchedEffect(Unit) {
            updateCurrentDate(it)
        }
    }

    if (loadMore) {
        LaunchedEffect(Unit) {
            loadNextWeek()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        WeekCalendar(
            days = calendarDays,
            selectedDate = selectedDate,
            onDateClicked = updateScrollToDate
        )
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = SizeS, start = SizeS, end = SizeS),
            verticalArrangement = Arrangement.spacedBy(SizeS)
        ) {
            items(
                items = data,
                key = { item ->
                    when (item) {
                        is ShiftListItem.Shift -> item.shiftId.toString()
                        is ShiftListItem.Header -> item.date
                    }
                },
                contentType = { item -> item.contentType }
            ) {
                when (it) {
                    is ShiftListItem.Shift -> ShiftItem(shift = it, onClick = onItemClicked)
                    is ShiftListItem.Header -> Text(
                        modifier = Modifier
                            .padding(top = SizeS),
                        text = it.date.getDateLabel(LocalContext.current),
                        style = MaterialTheme.typography.h4
                    )
                }
            }
        }
    }
}

const val SHIFT_LIST_SCREEN_ROUTE = "shifts_list"
