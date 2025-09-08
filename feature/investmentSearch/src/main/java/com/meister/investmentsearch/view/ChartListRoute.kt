package com.meister.investmentsearch.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.design_system.component.indicator.CircularLoadingIndicator
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.icon.RightArrowIcon
import com.jusicool.design_system.icon.SearchIcon
import com.jusicool.design_system.icon.UnionIcon
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.meister.investmentsearch.component.RecentSearchTag
import com.meister.investmentsearch.viewModel.ChartListUiState
import com.meister.investmentsearch.viewModel.ChartListViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ChartListRoute(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    navigateToChart: (String, String) -> Unit,
    viewModel: ChartListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo }
            .collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                if (lastVisibleIndex >= totalItems - 1 && !uiState.isLoading) {
                    viewModel.loadNextPage()
                }

                viewModel.setCurrentPage(lastVisibleIndex / ChartListViewModel.PAGE_SIZE)
            }
    }

    ChartListScreen(
        modifier = modifier,
        uiState = uiState,
        lazyListState = lazyListState,
        onSearchCLick = onSearchClick,
        navigateToChart = navigateToChart,
    )
}


@Composable
internal fun ChartListScreen(
    modifier: Modifier = Modifier,
    uiState: ChartListUiState,
    lazyListState: LazyListState,
    onSearchCLick: () -> Unit,
    navigateToChart: (String, String) -> Unit,
) {
    JusicoolTheme { colors, _ ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white),
        ) {
            JusicoolTopBar(
                modifier = Modifier.fillMaxWidth(),
                startIcon = { UnionIcon() },
                endIcon = {
                    SearchIcon(modifier = Modifier.clickable(onClick = onSearchCLick))
                }
            )

            if (uiState.resentSearchTagData.isNotEmpty()) {
                RecentSearchSection(data = uiState.resentSearchTagData)

                Spacer(Modifier.height(16.dp))
            }

            ChartListSection(
                data = uiState.chartListData,
                isInitialLoad = uiState.isInitialLoad,
                isLoading = uiState.isLoading,
                navigateToChart = navigateToChart,
                lazyListState = lazyListState,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChartListScreenPreview() {
    ChartListScreen(
        uiState = ChartListUiState(
            isLoading = false,
            resentSearchTagData = persistentListOf(
                InvestmentSearchTagData(
                    investmentName = "Apple Inc.",
                    investmentChangeRate = 1.5,
                    onClearClick = {}
                ),
                InvestmentSearchTagData(
                    investmentName = "Microsoft Corporation",
                    investmentChangeRate = -2.3,
                    onClearClick = {}
                ),
                InvestmentSearchTagData(
                    investmentName = "Amazon.com, Inc.",
                    investmentChangeRate = 0.0,
                    onClearClick = {},
                ),
                InvestmentSearchTagData(
                    investmentName = "Google LLC",
                    investmentChangeRate = 3.1,
                    onClearClick = {},
                )
            ),
            chartListData = persistentListOf(
                RecommendMarketWithPrice(
                    id = 1,
                    market = "NASDAQ",
                    marketType = MarketType.STOCK,
                    koreanName = "Apple Inc.",
                    englishName = "Apple",
                    logoUrl = "https://example.com/apple-logo.png",
                    currentPrice = 1111131.0,
                    profitRate = 0.05
                ),
                RecommendMarketWithPrice(
                    id = 2,
                    market = "NASDAQ",
                    marketType = MarketType.STOCK,
                    koreanName = "Microsoft Corporation",
                    englishName = "Microsoft",
                    logoUrl = "https://example.com/microsoft-logo.png",
                    currentPrice = 950000.0,
                    profitRate = -0.02
                ),
                RecommendMarketWithPrice(
                    id = 3,
                    market = "NASDAQ",
                    marketType = MarketType.STOCK,
                    koreanName = "Amazon.com, Inc.",
                    englishName = "Amazon",
                    logoUrl = "https://example.com/amazon-logo.png",
                    currentPrice = 800000.0,
                    profitRate = 0.0
                ),
            )
        ),
        onSearchCLick = {},
        navigateToChart = { _, _ -> },
        lazyListState = rememberLazyListState(),
    )
}


@Composable
private fun RecentSearchSection(data: PersistentList<InvestmentSearchTagData>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(data, key = { _, item -> item.investmentName }) { _, item ->
            RecentSearchTag(
                investmentName = item.investmentName,
                investmentChangeRate = item.investmentChangeRate,
                onClearClick = item.onClearClick
            )
        }
    }
}

@Composable
private fun ChartListSection(
    data: PersistentList<RecommendMarketWithPrice>,
    isLoading: Boolean,
    isInitialLoad: Boolean,
    lazyListState: LazyListState,
    navigateToChart: (String, String) -> Unit
) {
    if (isLoading && isInitialLoad) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularLoadingIndicator(
                size = 64.dp,
                strokeWidth = 6.dp
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = lazyListState
        ) {
            itemsIndexed(
                items = data,
                key = { _, item -> item.market },
            ) { _, item ->
                ChartItem(
                    data = item,
                    navigateToChart = navigateToChart,
                )
            }
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularLoadingIndicator(
                        size = 64.dp,
                        strokeWidth = 6.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun ChartItem(
    data: RecommendMarketWithPrice,
    navigateToChart: (String, String) -> Unit
) {
    JusicoolTheme { colors, typography ->
        val textColor = if (data.isPositive) colors.error
        else if (data.isNegative) colors.main
        else colors.gray400

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .JusicoolClickable {
                    navigateToChart(data.market, data.koreanName)
                },
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
                    text = data.koreanName,
                    style = typography.subTitle,
                    color = colors.black,
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = "${data.currentPrice} 원",
                    style = typography.bodySmall,
                    color = colors.black,
                    textAlign = TextAlign.End,
                )

//                Text(
//                    text = "${data.profit.toSignedFormattedText()} (${abs(data.profitRate)}%)",
//                    style = typography.label,
//                    color = textColor,
//                )
            }
        }
    }
}