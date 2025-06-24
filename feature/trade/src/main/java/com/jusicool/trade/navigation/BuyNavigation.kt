package com.jusicool.trade.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.trade.view.BuyRoute
import com.jusicool.trade.view.enum.TradeType
import kotlin.reflect.KFunction5

const val buyRoute = "buyRoute"


fun NavController.navigateToBuyRoute(
    name: String,
    type: String,
    price: Long,
    krwBalance: Long,
    marketCode:String,
    navOptions: NavOptions? = null
) {
    this.navigate(("$buyRoute/$name/$type/$price/$krwBalance/$marketCode"), navOptions)
}

fun NavGraphBuilder.buyRoute(
    navigateToTradeCompleted: (String, Int, TradeType, String, Int) -> Unit,
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$buyRoute/{name}/{type}/{price}/{krwBalance}/{marketCode}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },
            navArgument("price") { type = NavType.LongType },
            navArgument("krwBalance") { type = NavType.LongType },
            navArgument("marketCode") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: ""
        val type = backStackEntry.arguments?.getString("type") ?: ""
        val price = backStackEntry.arguments?.getLong("price") ?: 0L
        val krwBalance = backStackEntry.arguments?.getLong("krwBalance") ?: 0L
        val marketCode = backStackEntry.arguments?.getString("marketCode") ?: ""
        BuyRoute (
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