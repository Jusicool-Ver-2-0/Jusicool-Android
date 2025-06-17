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
    quantity: Int,
    money: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(("$chartRoute/$marketCode/$koreanName/$quantity/$money"), navOptions)
}

fun NavGraphBuilder.chartRoute(
    popUpBackStack: () -> Unit,
    navigateToBuy: (String, Long) -> Unit,
    navigateToSell: (String, Int) -> Unit,
    navigateToReserveBuy: (String, Long) -> Unit,
    navigateToReserveSell: (String, Int) -> Unit,
) {
    composable(
        route = "$chartRoute/{marketCode}/{koreanName}/{quantity}/{money}",
        arguments = listOf(
            navArgument("marketCode") { type = NavType.StringType },
            navArgument("koreanName") { type = NavType.StringType },
            navArgument("quantity") { type = NavType.IntType },
            navArgument("money") { type = NavType.LongType }
        )
    ) { backStackEntry ->
        val marketCode = backStackEntry.arguments?.getString("marketCode") ?: ""
        val koreanName = backStackEntry.arguments?.getString("koreanName") ?: ""
        val quantity = backStackEntry.arguments?.getInt("quantity") ?: 0
        val money = backStackEntry.arguments?.getLong("money") ?: 0L
        ChartRoute(
            marketCode = marketCode,
            koreanName = koreanName,
            quantity = quantity,
            money = money,
            navigateToBuy = navigateToBuy,
            navigateToSell = navigateToSell,
            navigateToReserveBuy = navigateToReserveBuy,
            navigateToReserveSell = navigateToReserveSell,
            popUpBackStack = popUpBackStack
        )
    }
}