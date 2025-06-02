package com.jusicool.jusicool_android.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberJusicoolAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) : JusicoolAppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass
    ) {
        JusicoolAppState(
            windowSizeClass = windowSizeClass,
            coroutineScope = coroutineScope,
            navController = navController
        )
    }
}

@Stable
class JusicoolAppState(
    val windowSizeClass: WindowSizeClass,
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {
    /*navigateBar 함수 추가*/
}