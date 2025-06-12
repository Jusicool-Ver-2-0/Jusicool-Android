package com.meister.investmentsearch.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.toSignedText
import com.meister.investmentsearch.component.RecentSearchTag
import com.meister.investmentsearch.viewModel.ChartListUiState
import com.meister.investmentsearch.viewModel.ChartListViewModel
import com.school_of_company.design_system.icon.SearchIcon
import com.school_of_company.design_system.icon.UnionIcon
import kotlinx.collections.immutable.PersistentList

@Composable
internal fun ChartListRoute(
    modifier: Modifier = Modifier,
    onSearchCLick: () -> Unit,
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
                onSearchCLick = onSearchCLick
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
    JusicoolTheme { colors, typography ->
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
                AsyncImage(
                    model = data.logoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(2.dp, color = colors.gray100, CircleShape)
                        .background(color = colors.white),
                )


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
                    text = data.priceChange.toSignedText(),
                    style = typography.bodySmall,
                    color = textColor
                )

                Text(
                    text = "(${data.percentageChange}%)",
                    style = typography.label
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
