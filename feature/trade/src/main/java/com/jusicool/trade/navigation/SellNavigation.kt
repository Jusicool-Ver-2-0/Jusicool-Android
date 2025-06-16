package com.jusicool.trade.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.trade.view.SellRoute

const val sellRoute = "sellRoute"


fun NavController.navigateToSellRoute(
    type: String,
    navOptions: NavOptions? = null
) {
    this.navigate(("$sellRoute/$type"), navOptions)
}

fun NavGraphBuilder.sellRoute(
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$sellRoute/{type}",
        arguments = listOf(
            navArgument("type") { type = NavType.StringType },
        )
    ) { backStackEntry ->
        val type = backStackEntry.arguments?.getString("type") ?: ""
        SellRoute (
            type = type,
            popUpBackStack = popUpBackStack
        )
    }
}