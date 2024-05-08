package com.example.app_barber.Funzioni.Schermo

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun height_Schermo_dp(): Dp {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    return screenHeight
}

@Composable
fun Width_Schermo_dp(): Dp {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    return screenWidth
}