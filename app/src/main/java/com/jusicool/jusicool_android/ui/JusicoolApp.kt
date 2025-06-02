package com.jusicool.jusicool_android.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jusicool.jusicool_android.navigation.JusicoolNavHost

@Composable
fun JusicoolApp(
    windowSizeClass: WindowSizeClass,
    appState: JusicoolAppState = rememberJusicoolAppState(windowSizeClass = windowSizeClass)
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = Color.White,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            /*TODO()*/
        }

    ) { paddingValues ->
        JusicoolNavHost(
            modifier = Modifier.padding(paddingValues = paddingValues),
            navController = appState.navController
        )
    }
}