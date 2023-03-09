package com.shiftkey.codingchallenge.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun WeekCalendar(
    days: List<LocalDate>,
    selectedDate: LocalDate,
    onDateClicked: (LocalDate) -> Unit
) {

    val listState = rememberLazyListState()

    LaunchedEffect(selectedDate) {
        listState.animateScrollToItem(days.indexOf(selectedDate))
    }

    Card(
        shape = RectangleShape,
        elevation = 8.dp
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(vertical = 16.dp),
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            items(days) {
                CalendarCell(
                    isSelected = it == selectedDate,
                    date = it,
                    onDateClicked = onDateClicked
                )
            }
        }
    }
}

@Composable
internal fun CalendarCell(
    isSelected: Boolean,
    date: LocalDate,
    onDateClicked: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onDateClicked(date) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.dayOfWeek.name.first().toString(),
            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface)
        )
        Text(
            modifier = Modifier
                .size(24.dp)
                .let {
                    if (isSelected) {
                        it.background(MaterialTheme.colors.primary, CircleShape)
                    } else {
                        it
                    }
                },
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.body2.copy(
                color = if (!isSelected) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.surface
                }
            ),
            textAlign = TextAlign.Center
        )
    }
}
