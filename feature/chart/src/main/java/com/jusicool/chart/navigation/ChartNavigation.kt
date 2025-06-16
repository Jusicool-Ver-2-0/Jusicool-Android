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
    navOptions: NavOptions? = null
) {
    this.navigate(("$chartRoute/$marketCode/$koreanName"), navOptions)
}

fun NavGraphBuilder.chartRoute(
    popUpBackStack: () -> Unit
) {
    composable(
        route = "$chartRoute/{marketCode}/{koreanName}",
        arguments = listOf(
            navArgument("marketCode") { type = NavType.StringType },
            navArgument("koreanName") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val marketCode = backStackEntry.arguments?.getString("marketCode") ?: ""
        val koreanName = backStackEntry.arguments?.getString("koreanName") ?: ""
        ChartRoute(
            marketCode = marketCode,
            koreanName = koreanName,
            popUpBackStack = popUpBackStack
        )
    }
}