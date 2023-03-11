package com.shiftkey.codingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shiftkey.codingchallenge.design.theme.ShiftKeyTheme
import com.shiftkey.codingchallenge.design.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            LaunchedEffect(true) {
                systemUiController.setSystemBarsColor(
                    color = White,
                    darkIcons = useDarkIcons
                )
            }
            ShiftKeyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainActivityNavigation()
                }
            }
        }
    }
}
