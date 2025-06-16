package com.jusicool.trade.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.trade.view.BuyReserveRoute

const val buyReserveRoute = "buyReserveRoute"


fun NavController.navigateToBuyReserveRoute(
    type: String,
    price: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(("$buyReserveRoute/$type/$price"), navOptions)
}

fun NavGraphBuilder.buyReserveRoute(
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$buyReserveRoute/{type}/{price}",
        arguments = listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("price") { type = NavType.LongType }
        )
    ) { backStackEntry ->
        val type = backStackEntry.arguments?.getString("type") ?: ""
        val price = backStackEntry.arguments?.getLong("price") ?: 0L
        BuyReserveRoute(
            type = type,
            price = price,
            popUpBackStack = popUpBackStack
        )
    }
}