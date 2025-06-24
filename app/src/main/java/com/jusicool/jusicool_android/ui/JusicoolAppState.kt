package com.jusicool.jusicool_android.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberJusicoolAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): JusicoolAppState {
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
    val tabs = listOf(
        TopLevelDestination.ASSET,
        TopLevelDestination.CHART,
        TopLevelDestination.NEWS,
        TopLevelDestination.MY,
    )

    // 현재 활성화된 경로(Route)
    val currentRoute: String?
        get() = navController.currentDestination?.route

    val shouldShowBottomBar: Boolean
        @Composable get() =
            navController.currentBackStackEntryAsState().value?.destination?.route in
                    tabs.map { it.route }
}
