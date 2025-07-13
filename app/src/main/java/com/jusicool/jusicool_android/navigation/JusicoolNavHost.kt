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
import com.jusicool.trade.navigation.navigateToBuyReserveRoute
import com.jusicool.trade.navigation.navigateToBuyRoute
import com.jusicool.trade.navigation.navigateToSellReserveRoute
import com.jusicool.trade.navigation.navigateToSellRoute
import com.jusicool.trade.navigation.navigateToTradeCompletedRoute
import com.jusicool.trade.navigation.sellReserveRoute
import com.jusicool.trade.navigation.sellRoute
import com.jusicool.trade.navigation.tradeCompletedRoute
import com.meister.community.navigation.navigateToWritePostRoute
import com.meister.community.navigation.writePostRoute
import com.meister.investmentsearch.navigation.chartListRoute
import com.meister.investmentsearch.navigation.investmentSearchRoute
import com.meister.investmentsearch.navigation.navigateToInvestmentSearchRoute
import com.meister.monthlyearnings.navigation.monthlyEarningsRoute
import com.meister.monthlyearnings.navigation.navigateToMonthlyEarningsRoute
import com.meister.orderhistory.navigation.navigateToOrderHistoryRoute
import com.meister.orderhistory.navigation.orderHistoryRoute

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
            navigateToOrderHistory = navController::navigateToOrderHistoryRoute,
            navigateToMonthlyEarningsRoute = navController::navigateToMonthlyEarningsRoute,
            navigateToChart = navController::navigateToChartRoute
        )

        monthlyEarningsRoute(
            popBackStack = navController::popBackStack
        )

        chartRoute(
            popUpBackStack = navController::popBackStack,
            navigateToBuy = navController::navigateToBuyRoute,
            navigateToSell = navController::navigateToSellRoute,
            navigateToReserveBuy = navController::navigateToBuyReserveRoute,
            navigateToReserveSell = navController::navigateToSellReserveRoute,
            navigateToCommunityPost = navController::navigateToWritePostRoute
        )

        chartListRoute(
            navigateToSearchInvestmentRoute = navController::navigateToInvestmentSearchRoute,
            navigateToChart = { marketCode, koreanName ->
                navController.navigateToChartRoute(
                    marketCode = marketCode,
                    koreanName = koreanName,
                    type = "CRYPTO",
                    quantity = 0,
                    money = 100002316431413L,
                    krwBalance = 0L
                )
            }
        )

        buyRoute(
            navigateToTradeCompleted = navController::navigateToTradeCompletedRoute,
            popUpBackStack = navController::popBackStack
        )

        sellRoute(
            navigateToTradeCompleted = navController::navigateToTradeCompletedRoute,
            popUpBackStack = navController::popBackStack
        )

        buyReserveRoute(
            navigateToTradeCompleted = navController::navigateToTradeCompletedRoute,
            popUpBackStack = navController::popBackStack
        )

        sellReserveRoute(
            navigateToTradeCompleted = navController::navigateToTradeCompletedRoute,
            popUpBackStack = navController::popBackStack
        )

        tradeCompletedRoute(
            navigateToAccount = navController::navigateToAccountRoute,
            navigateToOrderHistory = {}
        )

        orderHistoryRoute(
            popBackStack = navController::popBackStack
        )

        investmentSearchRoute(
            navigateToChart = { marketCode, koreanName ->
                navController.navigateToChartRoute(
                    marketCode = marketCode,
                    koreanName = koreanName,
                    type = "CRYPTO",
                    quantity = 0,
                    money = 100002316431413L,
                    krwBalance = 0L
                )
            },
            popBackStack = navController::popBackStack
        )

        writePostRoute(
            popUpBackStack = navController::popBackStack
        )
    }
}