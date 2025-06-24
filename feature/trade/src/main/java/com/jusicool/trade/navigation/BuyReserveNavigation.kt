package com.jusicool.trade.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.trade.view.BuyReserveRoute
import com.jusicool.trade.view.enum.TradeType

const val buyReserveRoute = "buyReserveRoute"

fun NavController.navigateToBuyReserveRoute(
    name: String,
    type: String,
    price: Long,
    krwBalance: Long,
    marketCode: String,
    navOptions: NavOptions? = null
) {
    this.navigate(("$buyReserveRoute/$name/$type/$price/$krwBalance/$marketCode"), navOptions)
}

fun NavGraphBuilder.buyReserveRoute(
    navigateToTradeCompleted: (String, Int, TradeType, String, Int) -> Unit,
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$buyReserveRoute/{name}/{type}/{price}/{krwBalance}/{marketCode}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },
            navArgument("price") { type = NavType.LongType },
            navArgument("krwBalance") { type = NavType.LongType },
            navArgument("marketCode") { type = NavType.StringType },
        )
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val type = backStackEntry.arguments?.getString("type") ?: ""
        val price = backStackEntry.arguments?.getLong("price") ?: 0L
        val krwBalance = backStackEntry.arguments?.getLong("krwBalance") ?: 0L
        val marketCode = backStackEntry.arguments?.getString("marketCode") ?: ""
        BuyReserveRoute(
            name = name,
            type = type,
            price = price,
            krwBalance = krwBalance,
            marketCode = marketCode,
            navigateToTradeCompleted = navigateToTradeCompleted,
            popUpBackStack = popUpBackStack
        )
    }
}