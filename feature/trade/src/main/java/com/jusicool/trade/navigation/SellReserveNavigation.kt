package com.jusicool.trade.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.trade.view.SellReserveRoute
import com.jusicool.trade.view.SellRoute
import com.jusicool.trade.view.enum.TradeType

const val sellReserveRoute = "sellReserveRoute"


fun NavController.navigateToSellReserveRoute(
    name: String,
    type: String,
    quantity: Int,
    marketCode: String,
    navOptions: NavOptions? = null
) {
    this.navigate(("$sellReserveRoute/$name/$type/$quantity/$marketCode"), navOptions)
}

fun NavGraphBuilder.sellReserveRoute(
    navigateToTradeCompleted: (String, Int, TradeType, String, Int) -> Unit,
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$sellReserveRoute/{name}/{type}/{quantity}/{marketCode}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },
            navArgument("quantity") { type = NavType.IntType },
            navArgument("marketCode") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val type = backStackEntry.arguments?.getString("type") ?: ""
        val quantity = backStackEntry.arguments?.getInt("quantity") ?: 0
        val marketCode = backStackEntry.arguments?.getString("marketCode") ?: ""

        SellReserveRoute(
            name = name,
            type = type,
            quantity = quantity,
            navigateToTradeCompleted = navigateToTradeCompleted,
            marketCode =marketCode,
            popUpBackStack = popUpBackStack
        )
    }
}