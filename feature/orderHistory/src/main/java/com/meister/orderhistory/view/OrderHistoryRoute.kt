package com.meister.orderhistory.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.entity.orderHistory.OrderHistory
import com.jusicool.utils.formatMoney
import com.meister.orderhistory.viewModel.CompletedOrderHistoryUiState
import com.meister.orderhistory.viewModel.OrderHistoryViewModel
import com.meister.orderhistory.viewModel.ReservedOrderHistoryUiState
import com.school_of_company.design_system.icon.LeftClarityArrowLineIcon
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
internal fun OrderHistoryRoute(
    modifier: Modifier = Modifier,
    viewModel: OrderHistoryViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val reservedOrderHistoryUiState by viewModel.reservedOrderHistoryUiState.collectAsStateWithLifecycle()
    val completedOrderHistoryUiState by viewModel.completedOrderHistoryUiState.collectAsStateWithLifecycle()

    OrderHistoryScreen(
        modifier = modifier,
        reservedOrderHistoryUiState = reservedOrderHistoryUiState,
        completedOrderHistoryUiState = completedOrderHistoryUiState,
        refreshReservedOrders = viewModel::refreshReservedOrders,
        refreshCompletedOrders = viewModel::refreshCompletedOrders,
        popBackStack = popBackStack
    )
}

@Composable
private fun OrderHistoryScreen(
    modifier: Modifier = Modifier,
    reservedOrderHistoryUiState: ReservedOrderHistoryUiState,
    completedOrderHistoryUiState: CompletedOrderHistoryUiState,
    refreshReservedOrders: () -> Unit,
    refreshCompletedOrders: () -> Unit,
    popBackStack: () -> Unit,
) {
    JusicoolTheme { colors, _ ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            JusicoolTopBar(
                modifier = Modifier.fillMaxWidth(),
                startIcon = { LeftClarityArrowLineIcon(modifier = Modifier.JusicoolClickable(onClick = popBackStack)) },
                betweenText = "주문 내역",
            )

            OrderHistoryTabLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                completedOrderData = completedOrderHistoryUiState,
                reservedOrderData = reservedOrderHistoryUiState,
                refreshReservedOrders = refreshReservedOrders,
                refreshCompletedOrders = refreshCompletedOrders,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun OrderHistoryScreenPreview() {
    val completedOrders = persistentListOf(
        OrderHistory(
            id = 1,
            market = "KRW-BTC",
            orderType = com.jusicool.entity.orderHistory.OrderType.BUY,
            reserveType = com.jusicool.entity.orderHistory.ReserveType.NOW,
            quantity = 3,
            price = 50000,
            status = com.jusicool.entity.orderHistory.OrderStatus.COMPLETED
        ),
        OrderHistory(
            id = 2,
            market = "KRW-ETH",
            orderType = com.jusicool.entity.orderHistory.OrderType.SELL,
            reserveType = com.jusicool.entity.orderHistory.ReserveType.NOW,
            quantity = 1,
            price = 35000,
            status = com.jusicool.entity.orderHistory.OrderStatus.COMPLETED
        ),
        OrderHistory(
            id = 3,
            market = "KRW-XRP",
            orderType = com.jusicool.entity.orderHistory.OrderType.BUY,
            reserveType = com.jusicool.entity.orderHistory.ReserveType.NOW,
            quantity = 10,
            price = 600,
            status = com.jusicool.entity.orderHistory.OrderStatus.COMPLETED
        ),
        OrderHistory(
            id = 4,
            market = "KRW-SOL",
            orderType = com.jusicool.entity.orderHistory.OrderType.SELL,
            reserveType = com.jusicool.entity.orderHistory.ReserveType.NOW,
            quantity = 2,
            price = 120000,
            status = com.jusicool.entity.orderHistory.OrderStatus.COMPLETED
        )
    )

    val reservedOrders = persistentListOf(
        OrderHistory(
            id = 5,
            market = "KRW-ADA",
            orderType = com.jusicool.entity.orderHistory.OrderType.SELL,
            reserveType = com.jusicool.entity.orderHistory.ReserveType.RESERVE,
            quantity = 5,
            price = 1500,
            status = com.jusicool.entity.orderHistory.OrderStatus.PENDING
        ),
        OrderHistory(
            id = 6,
            market = "KRW-DOGE",
            orderType = com.jusicool.entity.orderHistory.OrderType.BUY,
            reserveType = com.jusicool.entity.orderHistory.ReserveType.RESERVE,
            quantity = 20,
            price = 100,
            status = com.jusicool.entity.orderHistory.OrderStatus.PENDING
        ),
        OrderHistory(
            id = 7,
            market = "KRW-DOT",
            orderType = com.jusicool.entity.orderHistory.OrderType.SELL,
            reserveType = com.jusicool.entity.orderHistory.ReserveType.RESERVE,
            quantity = 4,
            price = 8000,
            status = com.jusicool.entity.orderHistory.OrderStatus.PENDING
        ),
        OrderHistory(
            id = 8,
            market = "KRW-LINK",
            orderType = com.jusicool.entity.orderHistory.OrderType.BUY,
            reserveType = com.jusicool.entity.orderHistory.ReserveType.RESERVE,
            quantity = 3,
            price = 9000,
            status = com.jusicool.entity.orderHistory.OrderStatus.PENDING
        )
    )

    val completedOrderHistoryUiState = CompletedOrderHistoryUiState(
        isLoading = false,
        completedOrderData = completedOrders,
        errorMessage = null
    )

    val reservedOrderHistoryUiState = ReservedOrderHistoryUiState(
        isLoading = false,
        reservedOrderData = reservedOrders,
        errorMessage = null
    )

    OrderHistoryScreen(
        popBackStack = {},
        reservedOrderHistoryUiState = reservedOrderHistoryUiState,
        completedOrderHistoryUiState = completedOrderHistoryUiState,
        refreshCompletedOrders = {},
        refreshReservedOrders = {},
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OrderHistoryTabLayout(
    modifier: Modifier = Modifier,
    completedOrderData: CompletedOrderHistoryUiState,
    reservedOrderData: ReservedOrderHistoryUiState,
    refreshReservedOrders: () -> Unit,
    refreshCompletedOrders: () -> Unit,
) {
    JusicoolTheme { colors, typography ->

        val tabTitles = listOf("완료된 주문", "주문 예약")
        val pagerState = rememberPagerState { tabTitles.size }
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = modifier) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .height(1.dp),
                        color = colors.black
                    )
                },
                containerColor = colors.white,
            ) {
                tabTitles.forEachIndexed { index, title ->
                    val isSelected = pagerState.currentPage == index

                    Tab(
                        selected = isSelected,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        text = {
                            Text(
                                text = title,
                                style = typography.subTitle,
                                color = if (isSelected) colors.black else colors.gray200
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> CompletedOrderList(
                        data = completedOrderData,
                        refreshCompletedOrders = refreshCompletedOrders,
                    )

                    1 -> ReservedOrderList(
                        data = reservedOrderData,
                        refreshReservedOrders = refreshReservedOrders,
                    )
                }
            }
        }
    }
}

@Composable
private fun CompletedOrderList(
    data: CompletedOrderHistoryUiState,
    refreshCompletedOrders: () -> Unit,
) {
    val swipeRefreshState = rememberSwipeRefreshState(data.isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = refreshCompletedOrders,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            when {
                data.isLoading -> {}
                data.errorMessage != null -> {}
                else -> {

                    items(items = data.completedOrderData, key = { item -> item.id }) { item ->
                        OrderHistoryItem(data = item)
                    }
                }
            }
        }
    }
}

@Composable
private fun ReservedOrderList(
    data: ReservedOrderHistoryUiState,
    refreshReservedOrders: () -> Unit,
) {
    val swipeRefreshState = rememberSwipeRefreshState(data.isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = refreshReservedOrders,
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            when {
                data.isLoading -> {}
                data.errorMessage != null -> {}
                else -> {
                    items(items = data.reservedOrderData, key = { item -> item.id }) { item ->
                        OrderHistoryItem(data = item)
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderHistoryItem(data: OrderHistory) {
    JusicoolTheme { colors, typography ->

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = data.market,
                style = typography.bodySmall,
            )

            Text(
                text = "${data.calculateTotalPrice().formatMoney()}" +
                        "원 구매${if (data.isBuyOrder()) "완료" else "예약"}",
                style = typography.label,
                color = if (data.isBuyOrder()) colors.error else colors.main,
            )
        }
    }
}