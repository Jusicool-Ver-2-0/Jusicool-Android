package com.meister.community.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.meister.community.view.WritePostRoute

const val writePostRoute = "writePostRoute"

fun NavController.navigateToWritePostRoute(
    marketCode: String,
    navOptions: NavOptions? = null
) {
    this.navigate(("$writePostRoute/$marketCode"), navOptions)
}

fun NavGraphBuilder.writePostRoute(
    popUpBackStack: () -> Unit,
) {
    composable(
        route = "$writePostRoute/{marketCode}",
        arguments = listOf(
            navArgument("marketCode") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val marketCode = backStackEntry.arguments?.getString("marketCode") ?: ""

        WritePostRoute(
            marketCode = marketCode,
            popUpBackStack = popUpBackStack
        )
    }
}