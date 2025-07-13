package com.meister.monthlyearnings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.meister.monthlyearnings.view.MonthlyEarningsRoute

const val monthlyEarningsRoute = "monthlyEarningsRoute"

fun NavController.navigateToMonthlyEarningsRoute(
    navOptions: NavOptions? = null
) {
    this.navigate(monthlyEarningsRoute, navOptions)
}

fun NavGraphBuilder.monthlyEarningsRoute(
    popBackStack: () -> Unit,
) {
    composable(route = monthlyEarningsRoute) {
        MonthlyEarningsRoute(popBackStack = popBackStack)
    }
}