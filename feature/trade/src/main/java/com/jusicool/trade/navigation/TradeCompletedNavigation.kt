package com.jusicool.trade.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.trade.view.TradeCompletedRoute
import com.jusicool.trade.view.enum.TradeType

const val tradeCompletedRoute = "tradeCompletedRoute"


fun NavController.navigateToTradeCompletedRoute(
    name: String,
    quantity: Int,
    tradeType: TradeType,
    investmentType: String,
    navOptions: NavOptions? = null
) {
    this.navigate(("$tradeCompletedRoute/$name/$quantity/${tradeType.name}/$investmentType"), navOptions)
}

fun NavGraphBuilder.tradeCompletedRoute(
    navigateToAccount: () -> Unit,
    navigateToOrderHistory: () -> Unit
) {
    composable(
        route = "$tradeCompletedRoute/{name}/{quantity}/{tradeType}/{investmentType}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("quantity") { type = NavType.IntType },
            navArgument("tradeType") { type = NavType.StringType },
            navArgument("investmentType") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val quantity = backStackEntry.arguments?.getInt("quantity") ?: 0
        val tradeTypeStr = backStackEntry.arguments?.getString("tradeType") ?: TradeType.BUY.name
        val investmentType = backStackEntry.arguments?.getString("investmentType") ?: ""

        val tradeType = try {
            TradeType.valueOf(tradeTypeStr)
        } catch (e: IllegalArgumentException) {
            TradeType.BUY
        }

        TradeCompletedRoute(
            name = name,
            quantity = quantity,
            tradeType = tradeType,
            investmentType = investmentType,
            navigateToAccount = navigateToAccount,
            navigateToOrderHistory = navigateToOrderHistory
        )
    }
}