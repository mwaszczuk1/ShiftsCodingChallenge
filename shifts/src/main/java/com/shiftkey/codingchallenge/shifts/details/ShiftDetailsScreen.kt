package com.shiftkey.codingchallenge.shifts.details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.shiftkey.codingchallenge.design.components.topBar.LocalTopBar

@Composable
fun ShiftDetailsScreen(
    viewModel: ShiftDetailsViewModel = hiltViewModel()
) {

    with(LocalTopBar.current) {
        toggleNavigationIcon(true)
        setTitle("Shift Details")
    }
}

const val SHIFT_DETAILS_SCREEN_ROUTE = "shift_details"
