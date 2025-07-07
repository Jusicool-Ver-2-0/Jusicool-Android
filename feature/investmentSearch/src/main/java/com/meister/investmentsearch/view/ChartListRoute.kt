package com.meister.investmentsearch.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.toSignedFormattedText
import com.meister.investmentsearch.component.RecentSearchTag
import com.meister.investmentsearch.viewModel.ChartListUiState
import com.meister.investmentsearch.viewModel.ChartListViewModel
import com.jusicool.design_system.icon.RightArrowIcon
import com.jusicool.design_system.icon.SearchIcon
import com.jusicool.design_system.icon.UnionIcon
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.abs

@Composable
internal fun ChartListRoute(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    viewModel: ChartListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    when {
        uiState.isLoading -> {}
        uiState.errorMessage != null -> {}
        else -> {
            ChartListScreen(
                modifier = modifier,
                uiState = uiState,
                onSearchCLick = onSearchClick
            )
        }
    }
}


@Composable
internal fun ChartListScreen(
    modifier: Modifier = Modifier,
    uiState: ChartListUiState,
    onSearchCLick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
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

        ChartListSection(data = uiState.chartListData)
    }
}

@Preview
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
                ), InvestmentSearchTagData(
                    investmentName = "Google LLC",
                    investmentChangeRate = 3.1,
                    onClearClick = {},
                )
            ),
            chartListData = persistentListOf(
                ChartItemData(
                    name = "Apple Inc.",
                    logoUrl = "https://example.com/apple-logo.png",
                    priceChange = 1.5,
                    percentageChange = 0.5,
                ),
                ChartItemData(
                    name = "Microsoft Corporation",
                    logoUrl = "https://example.com/microsoft-logo.png",
                    priceChange = -2.3,
                    percentageChange = -0.7,
                ),
                ChartItemData(
                    name = "Amazon.com, Inc.",
                    logoUrl = "https://example.com/amazon-logo.png",
                    priceChange = 0.0,
                    percentageChange = 0.0,
                ),
                ChartItemData(
                    name = "Google LLC",
                    logoUrl = "https://example.com/google-logo.png",
                    priceChange = 3.1,
                    percentageChange = 1.2,
                ),
            ),
        ),
        onSearchCLick = {},
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
private fun ChartListSection(data: PersistentList<ChartItemData>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(data, key = { _, item -> item.name }) { _, item ->
            ChartItem(data = item)
        }
    }
}

@Composable
private fun ChartItem(data: ChartItemData) {
    JusicoolTheme { colors, typography ->
        val textColor = if (data.priceChange > 0.0) {
            colors.error
        } else if (data.priceChange == 0.0) {
            colors.gray400
        } else {
            colors.main
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
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

data class ChartItemData(
    val name: String,             // 이름 (예: "애플", "비트코인")
    val logoUrl: String?,         // 로고 URL 또는 리소스 ID (옵션)
    val priceChange: Double,         // 가격 변화 (예: +1111816)
    val percentageChange: Double  // 변화율 (예: 7.9)
)
