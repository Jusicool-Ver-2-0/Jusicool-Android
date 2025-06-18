package com.jusicool.chart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jusicool.chart.view.ChartRoute

const val chartRoute = "chartRoute"

fun NavController.navigateToChartRoute(
    marketCode: String,
    koreanName: String,
    type: String,
    quantity: Int,
    money: Long,
    krwBalance:Long,
    navOptions: NavOptions? = null
) {
    this.navigate(("$chartRoute/$marketCode/$koreanName/$type/$quantity/$money/$krwBalance"), navOptions)
}

fun NavGraphBuilder.chartRoute(
    popUpBackStack: () -> Unit,
    navigateToBuy: (String,String, Long, Long) -> Unit,
    navigateToSell: (String,String, Int) -> Unit,
    navigateToReserveBuy: (String,String, Long, Long) -> Unit,
    navigateToReserveSell: (String, String, Int) -> Unit,
) {
    composable(
        route = "$chartRoute/{marketCode}/{koreanName}/{type}/{quantity}/{money}/{krwBalance}",
        arguments = listOf(
            navArgument("marketCode") { type = NavType.StringType },
            navArgument("koreanName") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },
            navArgument("quantity") { type = NavType.IntType },
            navArgument("money") { type = NavType.LongType },
            navArgument("krwBalance") { type = NavType.LongType }
        )
    ) { backStackEntry ->
        val marketCode = backStackEntry.arguments?.getString("marketCode") ?: ""
        val koreanName = backStackEntry.arguments?.getString("koreanName") ?: ""
        val type = backStackEntry.arguments?.getString("type") ?: ""
        val quantity = backStackEntry.arguments?.getInt("quantity") ?: 0
        val money = backStackEntry.arguments?.getLong("money") ?: 0L
        val krwBalance = backStackEntry.arguments?.getLong("krwBalance") ?: 0L
        ChartRoute(
            marketCode = marketCode,
            koreanName = koreanName,
            quantity = quantity,
            money = money,
            krwBalance = krwBalance,
            type = type,
            navigateToBuy = navigateToBuy,
            navigateToSell = navigateToSell,
            navigateToReserveBuy = navigateToReserveBuy,
            navigateToReserveSell = navigateToReserveSell,
            popUpBackStack = popUpBackStack
        )
    }
}