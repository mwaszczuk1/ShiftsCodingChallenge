package com.shiftkey.codingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shiftkey.codingchallenge.design.theme.ShiftKeyTheme
import com.shiftkey.codingchallenge.design.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            LaunchedEffect(true) {
                systemUiController.setNavigationBarColor(
                    color = White,
                    darkIcons = useDarkIcons
                )
            }
            ShiftKeyTheme {
//                MainActivityComposable()
            }
        }
    }
}
