package com.jusicool.jusicool_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jusicool.account.navigation.accountRoute
import com.jusicool.account.navigation.navigateToAccountRoute
import com.jusicool.chart.navigation.chartRoute
import com.jusicool.chart.navigation.navigateToChartRoute
import com.jusicool.signin.navigation.signInRoute
import com.jusicool.trade.navigation.buyReserveRoute
import com.jusicool.trade.navigation.buyRoute
import com.jusicool.trade.navigation.sellReserveRoute
import com.jusicool.trade.navigation.sellRoute

@Composable
fun JusicoolNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = signInRoute
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        signInRoute(
            navigateToSignUp = { /*TODO()*/ },
            navigateToAccount = navController::navigateToAccountRoute
        )

        accountRoute(
            navigateToChart = navController::navigateToChartRoute
        )

        chartRoute(
            popUpBackStack = navController::popBackStack
        )

        buyRoute(
            popUpBackStack = navController::popBackStack
        )

        sellRoute(
            popUpBackStack = navController::popBackStack
        )

        buyReserveRoute(
            popUpBackStack = navController::popBackStack
        )

        sellReserveRoute(
            popUpBackStack = navController::popBackStack
        )
    }
}