package com.meister.community.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.meister.community.view.WriteDetailRoute
import com.meister.community.view.WritePostRoute

const val writePostRoute = "writePostRoute"

fun NavController.navigateToWritePostRoute(
    navOptions: NavOptions? = null
) {
    this.navigate(writePostRoute, navOptions)
}

fun NavGraphBuilder.writePostRoute(
    popUpBackStack: () -> Unit,
) {
    composable(writePostRoute) {
        WritePostRoute(
            popUpBackStack = popUpBackStack
        )
    }
}