package com.shiftkey.codingchallenge.design.components.topBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shiftkey.codingchallenge.design.DesignDrawables

@Composable
fun TopBar(
    navController: NavHostController
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        contentPadding = PaddingValues(horizontal = 16.dp),
        elevation = 0.dp
    ) {
        AnimatedVisibility(
            visible = LocalTopBar.current.state.value.isBackArrowVisible
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {
                        navController.navigateUp()
                    },
                painter = painterResource(
                    id = DesignDrawables.ic_arrow_back
                ),
                contentDescription = ""
            )
        }
        Text(
            text = LocalTopBar.current.state.value.title,
            style = MaterialTheme.typography.h2
        )
    }
}
