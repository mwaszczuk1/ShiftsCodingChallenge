package com.shiftkey.codingchallenge.shifts.list

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shiftkey.codingchallenge.design.components.WeekCalendar
import com.shiftkey.codingchallenge.design.components.topBar.LocalTopBar
import com.shiftkey.codingchallenge.domain.model.Shift
import com.shiftkey.codingchallenge.domain.model.ShiftsList
import com.shiftkey.codingchallenge.shifts.details.SHIFT_DETAILS_SCREEN_ROUTE
import java.time.LocalDate

@Composable
fun ShiftsListScreen(
    viewModel: ShiftsListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    with(LocalTopBar.current) {
        setTitle("Shifts")
        toggleNavigationIcon(false)
    }

    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        state.shifts?.let {
            ShiftsListLayout(
                data = it,
                scrollToDay = state.scrollToDate,
                selectedDate = state.selectedDate,
                updateSelectedDate = viewModel::selectDate,
                updateScrollToDate = viewModel::scrollToDay,
                loadNextWeek = viewModel::loadNextWeek,
                onItemClicked = { navController.navigate(SHIFT_DETAILS_SCREEN_ROUTE) }
            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            visible = state.isUpdating
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .shadow(elevation = 4.dp, shape = CircleShape)
                    .background(MaterialTheme.colors.surface, CircleShape)
                    .scale(0.8f)
            )
        }
    }

}

@Composable
fun ShiftsListLayout(
    data: ShiftsList,
    scrollToDay: LocalDate?,
    selectedDate: LocalDate,
    updateSelectedDate: (LocalDate) -> Unit,
    updateScrollToDate: (LocalDate) -> Unit,
    loadNextWeek: () -> Unit,
    onItemClicked: (Shift) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(scrollToDay) {
        scrollToDay?.let { date ->
            val dateIndex = data.shifts.toList().indexOfFirst { it.first == date }
            val index = data.shifts.toList()
                .subList(0, dateIndex).sumOf { (_, shifts) ->
                    1 + shifts.size
                }
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

    LaunchedEffect(loadMore) {
        if (loadMore) {
            loadNextWeek()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        WeekCalendar(
            days = data.shifts.keys.toList(),
            selectedDate = selectedDate,
            onDateClicked = updateScrollToDate
        )
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            data.shifts.forEach { (day, shifts) ->
                item(day) {
                    updateSelectedDate(day)
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        text = day.toString(),
                        style = MaterialTheme.typography.h3
                    )
                }
                items(shifts) {
                    ShiftItem(it, onItemClicked)
                }
            }
        }
    }

}



const val SHIFT_LIST_SCREEN_ROUTE = "shifts_list"
