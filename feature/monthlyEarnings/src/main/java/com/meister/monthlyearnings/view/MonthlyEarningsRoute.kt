package com.meister.monthlyearnings.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.toSignedFormattedText
import com.meister.monthlyearnings.viewModel.MonthlyEarningsViewModel
import com.meister.monthlyearnings.viewModel.uiState.MonthlyEarningsUiState
import com.school_of_company.design_system.icon.LeftClarityArrowLineIcon
import com.school_of_company.design_system.icon.RightArrowIcon
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

data class ChartItemData(
    val date: LocalDate,          // 날짜
    val name: String,             // 이름 (예: "애플", "비트코인")
    val logoUrl: String?,         // 로고 URL 또는 리소스 ID (옵션)
    val priceChange: Double,         // 가격 변화 (예: +1111816)
    val percentageChange: Double  // 변화율 (예: 7.9)
)


@Composable
internal fun MonthlyEarningsRoute(
    modifier: Modifier = Modifier,
    viewModel: MonthlyEarningsViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MonthlyEarningsScreen(
        modifier = modifier,
        uiState = uiState,
        popBackStack = popBackStack,
        refreshCryptoHoldings = { /* TODO: Implement refreshCryptoHoldings */ },
        refreshStockHoldings = { /* TODO: Implement refreshStockHoldings */ },
    )
}

@Composable
private fun MonthlyEarningsScreen(
    modifier: Modifier = Modifier,
    uiState: MonthlyEarningsUiState,
    refreshCryptoHoldings: () -> Unit,
    refreshStockHoldings: () -> Unit,
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
                betweenText = "이번 달 수익",
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {

                MiddleText(
                    earning = uiState.monthlyEarnings,
                    profitRate = uiState.monthlyReturnRate,
                )

                Spacer(modifier = Modifier.height(16.dp))
                
                MonthlyEarningsTabLayout(
                    isLoading = uiState.isLoading,
                    totalAssetsHoldingData = uiState.totalHoldingAssetsData,
                    cryptoHoldingData = uiState.cryptoHoldingsData,
                    stockHoldingData = uiState.stockHoldingsData,
                    refreshCryptoHoldings = refreshCryptoHoldings,
                    refreshStockHoldings = refreshStockHoldings,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MonthlyEarningsScreenPreview() {
    val sampleData = persistentListOf(
        ChartItemData(
            date = LocalDate.now(),
            name = "애플",
            logoUrl = null,
            priceChange = 1111816.0,
            percentageChange = 7.9
        ),
        ChartItemData(
            date = LocalDate.now().minusDays(1),
            name = "비트코인",
            logoUrl = null,
            priceChange = -523000.0,
            percentageChange = -3.2
        ),
        ChartItemData(
            date = LocalDate.now(),
            name = "삼성전자",
            logoUrl = null,
            priceChange = 0.0,
            percentageChange = 0.0
        ),
        ChartItemData(
            date = LocalDate.now().minusDays(1),
            name = "테슬라",
            logoUrl = null,
            priceChange = 234000.0,
            percentageChange = 1.5
        ),
    )

    MonthlyEarningsScreen(
        uiState = MonthlyEarningsUiState(
            monthlyEarnings = 1234567,
            monthlyReturnRate = 5.3,
            isLoading = false,
            totalHoldingAssetsData = sampleData,
            cryptoHoldingsData = sampleData.filter { it.name == "비트코인" || it.name == "테슬라" }
                .toPersistentList(),
            stockHoldingsData = sampleData.filter { it.name == "애플" || it.name == "삼성전자" }
                .toPersistentList(),
        ),
        popBackStack = {},
        refreshCryptoHoldings = {},
        refreshStockHoldings = {},
    )
}


@Composable
private fun MiddleText(earning: Int, profitRate: Double) {
    JusicoolTheme { colors, typography ->
        val textColor = if (earning > 0.0) {
            colors.error
        } else if (earning == 0) {
            colors.gray400
        } else {
            colors.main
        }

        Text(
            text = "${earning.toSignedFormattedText()} (${profitRate}%)",
            style = typography.titleSmall,
            color = textColor
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MonthlyEarningsTabLayout(
    isLoading: Boolean,
    totalAssetsHoldingData: PersistentList<ChartItemData>,
    cryptoHoldingData: PersistentList<ChartItemData>,
    stockHoldingData: PersistentList<ChartItemData>,
    refreshCryptoHoldings: () -> Unit,
    refreshStockHoldings: () -> Unit,
) {
    JusicoolTheme { colors, typography ->

        val tabTitles = listOf("전체", "주식", "코인")
        val pagerState = rememberPagerState { tabTitles.size }
        val coroutineScope = rememberCoroutineScope()
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

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

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> AssetsHoldingList(
                    data = totalAssetsHoldingData,
                    swipeRefreshState = swipeRefreshState,
                    refreshAssetsHoldingData = {
                        refreshStockHoldings()
                        refreshCryptoHoldings()
                    },
                )

                1 -> AssetsHoldingList(
                    data = stockHoldingData,
                    swipeRefreshState = swipeRefreshState,
                    refreshAssetsHoldingData = refreshStockHoldings,
                )

                2 -> AssetsHoldingList(
                    data = cryptoHoldingData,
                    swipeRefreshState = swipeRefreshState,
                    refreshAssetsHoldingData = refreshCryptoHoldings,
                )
            }
        }
    }
}

@Composable
private fun AssetsHoldingList(
    data: PersistentList<ChartItemData>,
    refreshAssetsHoldingData: () -> Unit,
    swipeRefreshState: SwipeRefreshState
) {
    JusicoolTheme { _, typography ->
        val groupedItems = data.groupBy { it.date }


        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = refreshAssetsHoldingData,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                groupedItems.forEach { (date, items) ->
                    item {
                        Text(
                            text = date.format(DateTimeFormatter.ofPattern("M월 d일")),
                            style = typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    items(
                        items = items,
                        key = { "${it.logoUrl}_${it.name}" },
                    ) { item ->
                        MonthlyAssetsItem(data = item)

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthlyAssetsItem(modifier: Modifier = Modifier, data: ChartItemData) {
    JusicoolTheme { colors, typography ->
        val textColor = if (data.priceChange > 0.0) {
            colors.error
        } else if (data.priceChange == 0.0) {
            colors.gray400
        } else {
            colors.main
        }

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                /*AsyncImage(
                    model = data.logoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(2.dp, color = colors.gray100, CircleShape)
                        .background(color = colors.white),
                )*/
                RightArrowIcon(modifier = Modifier.size(40.dp))
                // TODO: 임시 코드

                Text(
                    text = data.name,
                    style = typography.subTitle
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = data.priceChange.toSignedFormattedText(),
                    style = typography.bodySmall,
                    color = textColor
                )

                Text(
                    text = "(${abs(data.percentageChange)}%)",
                    style = typography.label,
                    color = textColor,
                )
            }
        }
    }
}
