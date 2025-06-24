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
    marketCode: String,
    navOptions: NavOptions? = null
) {
    this.navigate(("$sellRoute/$name/$type/$quantity/$marketCode"), navOptions)
}

fun NavGraphBuilder.sellRoute(
    navigateToTradeCompleted: (String, Int, TradeType, String, Int) -> Unit,
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$sellRoute/{name}/{type}/{quantity}/{marketCode}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },
            navArgument("quantity") { type = NavType.IntType },
            navArgument("marketCode") { type = NavType.StringType },
        )
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val type = backStackEntry.arguments?.getString("type") ?: ""
        val quantity = backStackEntry.arguments?.getInt("quantity") ?: 0
        val marketCode = backStackEntry.arguments?.getString("marketCode") ?: ""
        SellRoute (
            name = name,
            type = type,
            quantity = quantity,
            marketCode = marketCode,
            navigateToTradeCompleted = navigateToTradeCompleted,
            popUpBackStack = popUpBackStack
        )
    }
}