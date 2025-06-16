package com.jusicool.trade.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.trade.view.SellReserveRoute
import com.jusicool.trade.view.SellRoute

const val sellReserveRoute = "sellReserveRoute"


fun NavController.navigateToSellReserveRoute(
    type: String,
    navOptions: NavOptions? = null
) {
    this.navigate(("$sellReserveRoute/$type"), navOptions)
}

fun NavGraphBuilder.sellReserveRoute(
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$sellReserveRoute/{type}",
        arguments = listOf(
            navArgument("type") { type = NavType.StringType },
        )
    ) { backStackEntry ->
        val type = backStackEntry.arguments?.getString("type") ?: ""
        SellReserveRoute(
            type = type,
            popUpBackStack = popUpBackStack
        )
    }
}