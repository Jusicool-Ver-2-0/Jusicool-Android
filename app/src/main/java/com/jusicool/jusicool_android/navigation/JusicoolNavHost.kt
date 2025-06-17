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
            popUpBackStack = navController::popBackStack,
            navigateToBuy = {_,_ ->},
            navigateToSell = {_,_ ->},
            navigateToReserveBuy = {_,_ ->},
            navigateToReserveSell ={_,_ ->}
        )

    }
}