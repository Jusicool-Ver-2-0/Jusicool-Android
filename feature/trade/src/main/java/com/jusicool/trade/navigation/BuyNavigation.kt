package com.jusicool.trade.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.trade.view.BuyRoute

const val buyRoute = "buyRoute"


fun NavController.navigateToBuyRoute(
    type: String,
    price: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(("$buyRoute/$type/$price"), navOptions)
}

fun NavGraphBuilder.buyRoute(
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$buyRoute/{type}/{price}",
        arguments = listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("price") { type = NavType.LongType }
        )
    ) { backStackEntry ->
        val type = backStackEntry.arguments?.getString("type") ?: ""
        val price = backStackEntry.arguments?.getLong("price") ?: 0L
        BuyRoute (
            type = type,
            price = price,
            popUpBackStack = popUpBackStack
        )
    }
}