package com.meister.orderhistory.view

import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.meister.orderhistory.viewModel.OrderHistoryUiState
import com.meister.orderhistory.viewModel.OrderHistoryViewModel
import com.school_of_company.design_system.icon.LeftClarityArrowLineIcon
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
internal fun OrderHistoryRoute(
    modifier: Modifier = Modifier,
    viewModel: OrderHistoryViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> {}
        uiState.errorMessage != null -> {}
        else -> {
            OrderHistoryScreen(
                modifier = modifier,
                uiState = uiState,
                popBackStack = popBackStack
            )
        }
    }
}

@Composable
private fun OrderHistoryScreen(
    modifier: Modifier = Modifier,
    uiState: OrderHistoryUiState,
    popBackStack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
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
            completedOrderData = uiState.completedOrderData,
            reservedOrderData = uiState.reservedOrderData
        )
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

    val uiState = OrderHistoryUiState(
        isLoading = false,
        errorMessage = null,
        completedOrderData = completedOrders,
        reservedOrderData = reservedOrders
    )

    OrderHistoryScreen(
        uiState = uiState,
        popBackStack = {}
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OrderHistoryTabLayout(
    modifier: Modifier = Modifier,
    completedOrderData: PersistentList<OrderHistory>,
    reservedOrderData: PersistentList<OrderHistory>,
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
                    0 -> OrderList(data = completedOrderData)
                    1 -> OrderList(data = reservedOrderData)
                }
            }
        }
    }
}

@Composable
fun OrderList(data: PersistentList<OrderHistory>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(items = data, key = { item -> item.id }) { item ->
            OrderHistoryItem(data = item)
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