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
    navOptions: NavOptions? = null
) {
    this.navigate(("$buyReserveRoute/$name/$type/$price/$krwBalance"), navOptions)
}

fun NavGraphBuilder.buyReserveRoute(
    navigateToTradeCompleted: (String, Int, TradeType, String) -> Unit,
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$buyReserveRoute/{name}/{type}/{price}/{krwBalance}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },
            navArgument("price") { type = NavType.LongType },
            navArgument("krwBalance") { type = NavType.LongType }
        )
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val type = backStackEntry.arguments?.getString("type") ?: ""
        val price = backStackEntry.arguments?.getLong("price") ?: 0L
        val krwBalance = backStackEntry.arguments?.getLong("krwBalance") ?: 0L
        BuyReserveRoute(
            name = name,
            type = type,
            price = price,
            krwBalance = krwBalance,
            navigateToTradeCompleted = navigateToTradeCompleted,
            popUpBackStack = popUpBackStack
        )
    }
}