package com.jusicool.chart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jusicool.chart.view.ChartRoute
import com.jusicool.chart.view.ChartScreenPreview

const val chartRoute = "chartRoute"

fun NavController.navigateToChartRoute(napOptions: NavOptions? = null) {
    this.navigate(chartRoute, napOptions)
}

fun NavGraphBuilder.chartRoute(
    popUpBackStack: () -> Unit
) {
    composable(chartRoute) {
        ChartRoute(
            popUpBackStack = popUpBackStack
        )
    }
}