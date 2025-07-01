package com.meister.orderhistory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.meister.orderhistory.view.OrderHistoryRoute

const val OrderHistoryRoute = "OrderHistoryRoute"

fun NavController.navigateToOrderHistoryRoute(napOptions: NavOptions? = null) {
    this.navigate(OrderHistoryRoute, napOptions)
}

fun NavGraphBuilder.orderHistoryRoute(
    popBackStack: () -> Unit,
) {
    composable(OrderHistoryRoute) {
        OrderHistoryRoute(
            popBackStack = popBackStack
        )
    }
}