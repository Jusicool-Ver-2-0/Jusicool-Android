package com.meister.investmentsearch.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.meister.investmentsearch.view.ChartListRoute

const val chartListRoute = "chartListRoute"

fun NavController.navigateToChartListRoute(
    navOptions: NavOptions? = null
) {
    this.navigate(chartListRoute, navOptions)
}

fun NavGraphBuilder.chartListRoute(
    navigateToSearchInvestmentRoute: () -> Unit,
) {
    composable(route = chartListRoute) {
        ChartListRoute(onSearchClick = navigateToSearchInvestmentRoute)
    }
}