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
    navOptions: NavOptions? = null
) {
    this.navigate(("$chartRoute/$marketCode/$koreanName/$quantity"), navOptions)
}

fun NavGraphBuilder.chartRoute(
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$chartRoute/{marketCode}/{koreanName}/{quantity}",
        arguments = listOf(
            navArgument("marketCode") { type = NavType.StringType },
            navArgument("koreanName") { type = NavType.StringType },
            navArgument("quantity") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val marketCode = backStackEntry.arguments?.getString("marketCode") ?: ""
        val koreanName = backStackEntry.arguments?.getString("koreanName") ?: ""
        val quantity = backStackEntry.arguments?.getInt("quantity") ?: 0
        ChartRoute(
            marketCode = marketCode,
            koreanName = koreanName,
            quantity = quantity,
            popUpBackStack = popUpBackStack
        )
    }
}