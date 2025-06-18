package com.jusicool.trade.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.trade.view.SellRoute
import com.jusicool.trade.view.enum.TradeType

const val sellRoute = "sellRoute"


fun NavController.navigateToSellRoute(
    name: String,
    type: String,
    quantity: Int,
    navOptions: NavOptions? = null
) {
    this.navigate(("$sellRoute/$name/$type/$quantity"), navOptions)
}

fun NavGraphBuilder.sellRoute(
    navigateToTradeCompleted: (String, Int, TradeType, String) -> Unit,
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$sellRoute/{name}/{type}/{quantity}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },
            navArgument("quantity") { type = NavType.IntType },
        )
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val type = backStackEntry.arguments?.getString("type") ?: ""
        val quantity = backStackEntry.arguments?.getInt("quantity") ?: 0
        SellRoute (
            name = name,
            type = type,
            quantity = quantity,
            navigateToTradeCompleted = navigateToTradeCompleted,
            popUpBackStack = popUpBackStack
        )
    }
}