package com.meister.investmentsearch.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.meister.investmentsearch.view.ChartListRoute
import com.meister.investmentsearch.view.InvestmentSearchRoute

const val chartListRoute = "chartListRoute"
const val investmentSearchRoute = "investmentSearchRoute"

fun NavController.navigateToChartListRoute(
    navOptions: NavOptions? = null
) {
    this.navigate(chartListRoute, navOptions)
}

fun NavController.navigateToInvestmentSearchRoute(
    navOptions: NavOptions? = null
) {
    this.navigate(investmentSearchRoute, navOptions)
}

fun NavGraphBuilder.chartListRoute(
    navigateToSearchInvestmentRoute: () -> Unit,
    navigateToChart: (String, String) -> Unit
) {
    composable(route = chartListRoute) {
        ChartListRoute(
            onSearchClick = navigateToSearchInvestmentRoute,
            navigateToChart = navigateToChart
        )
    }
}

fun NavGraphBuilder.investmentSearchRoute(
    popBackStack: () -> Unit,
) {
    composable(route = investmentSearchRoute) {
        InvestmentSearchRoute(
            popBackStack= popBackStack,
        )
    }
}