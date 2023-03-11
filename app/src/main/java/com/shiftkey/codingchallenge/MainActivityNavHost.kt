package com.shiftkey.codingchallenge

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.shiftkey.codingchallenge.design.anims.*
import com.shiftkey.codingchallenge.design.components.topBar.TopBar
import com.shiftkey.codingchallenge.shifts.details.SHIFT_DETAILS_SCREEN_ROUTE
import com.shiftkey.codingchallenge.shifts.details.ShiftDetailsScreen
import com.shiftkey.codingchallenge.shifts.list.ui.SHIFT_LIST_SCREEN_ROUTE
import com.shiftkey.codingchallenge.shifts.list.ui.ShiftsListScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainActivityNavigation() {
    val navController = rememberAnimatedNavController()

    Box(
        Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = { TopBar(navController) }
        ) {
            AnimatedNavHost(
                navController = navController,
                startDestination = SHIFT_LIST_SCREEN_ROUTE
            ) {
                composable(
                    route = SHIFT_LIST_SCREEN_ROUTE,
                    enterTransition = slideIntoContainerAnimRight(),
                    popEnterTransition = slideIntoContainerAnimRight(),
                    exitTransition = slideOutOfContainerAnimLeft(),
                    popExitTransition = slideOutOfContainerAnimLeft()
                ) {
                    ShiftsListScreen(navController = navController)
                }

                composable(
                    route = SHIFT_DETAILS_SCREEN_ROUTE,
                    enterTransition = slideIntoContainerAnimLeft(),
                    popEnterTransition = slideIntoContainerAnimLeft(),
                    exitTransition = slideOutOfContainerAnimRight(),
                    popExitTransition = slideOutOfContainerAnimRight()
                ) {
                    ShiftDetailsScreen()
                }
            }
        }
    }
}