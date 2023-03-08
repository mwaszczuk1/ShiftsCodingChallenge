package com.shiftkey.codingchallenge.shifts.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.model.ShiftsList
import timber.log.Timber


@Composable
fun ShiftsListScreen(
    viewModel: ShiftsListViewModel = hiltViewModel()
) {
    viewModel.state.collectAsState().value.let { state ->
        if (state is ViewState.Success) {
            ShiftsListLayout(data = state.data)
        } else if (state is ViewState.Error) {

        }
    }
}

@Composable
fun ShiftsListLayout(
    data: ShiftsList
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "${data.lat} ${data.lng}"
        )
    }
}
