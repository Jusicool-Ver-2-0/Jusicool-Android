package com.jusicool.jusicool_android.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jusicool.jusicool_android.navigation.JusicoolNavHost

@Composable
fun JusicoolApp(
    windowSizeClass: WindowSizeClass,
    startDestination: String,
    appState: JusicoolAppState = rememberJusicoolAppState(windowSizeClass = windowSizeClass)
) {
    Scaffold(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = Color.White,
        contentColor = Color.White,
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                JusicoolNavigationBar(appState = appState)
            }
        }
    ) { paddingValues ->
        JusicoolNavHost(
            modifier = Modifier.padding(paddingValues = paddingValues),
            navController = appState.navController,
            startDestination = startDestination,
        )
    }
}