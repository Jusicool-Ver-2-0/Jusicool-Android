package com.meister.assets.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.meister.assets.view.MonthlyIncomeRoute

const val monthlyIncomeRoute = "monthlyIncomeRoute"

fun NavController.navigateToMonthlyIncome(napOptions: NavOptions? = null) {
    this.navigate(monthlyIncomeRoute, napOptions)
}

fun NavGraphBuilder.monthlyIncomeRoute(
    popBackStack: () -> Unit,
) {
    composable(monthlyIncomeRoute) {
        MonthlyIncomeRoute(
            navigateToBack = popBackStack
        )
    }
}