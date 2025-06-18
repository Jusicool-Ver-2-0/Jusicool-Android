package com.jusicool.account.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jusicool.account.view.AccountRoute

const val accountRoute = "accountRoute"

fun NavController.navigateToAccountRoute(napOptions: NavOptions? = null) {
    this.navigate(accountRoute, napOptions)
}

fun NavGraphBuilder.accountRoute(
    navigateToChart: (marketCode: String, name: String, type: String, quantity: Int, money: Long, krwBalance: Long) -> Unit
) {
    composable(accountRoute) {
        AccountRoute(
            navigateToChart = navigateToChart
        )
    }
}