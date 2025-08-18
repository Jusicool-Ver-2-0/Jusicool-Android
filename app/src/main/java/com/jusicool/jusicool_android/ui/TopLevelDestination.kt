package com.jusicool.jusicool_android.ui

import com.jusicool.account.navigation.accountRoute
import com.jusicool.design_system.R
import com.meister.investmentsearch.navigation.chartListRoute

sealed class TopLevelDestination(val route: String, val icon: Int, val label: String) {
    object ASSET : TopLevelDestination(route = accountRoute, icon = R.drawable.pie_chartfilled, label = "자산")
    object CHART : TopLevelDestination(route = chartListRoute, icon = R.drawable.chart_line, label = "차트")
    object NEWS : TopLevelDestination(route = "newsRoute", icon = R.drawable.material_symbols_news_outline, label = "뉴스")
    object MY : TopLevelDestination(route = "myPage", icon = R.drawable.account, label = "마이 페이지")
}
