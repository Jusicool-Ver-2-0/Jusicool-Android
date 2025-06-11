package com.jusicool.chart.view

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.chart.component.RecentSearchTag
import com.jusicool.chart.viewModel.StockSearchUiState
import com.jusicool.chart.viewModel.StockSearchViewModel
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.TransparentTextField
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.FormatPercent
import com.school_of_company.design_system.icon.ClarityArrowLineIcon
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun StockSearchRoute(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit,
    viewModel: StockSearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    when {
        uiState.isLoading -> {}
        uiState.errorMessage != null -> {}
        else -> {
            StockSearchScreen(
                modifier = modifier,
                uiState = uiState,
                popUpBackStack = popUpBackStack,
                searchStock = viewModel::searchStock,
                onSearchTextChange = viewModel::onSearchTextChange,
            )
        }
    }
}

@Composable
private fun StockSearchScreen(
    modifier: Modifier = Modifier,
    uiState: StockSearchUiState,
    popUpBackStack: () -> Unit,
    searchStock: (String) -> Unit,
    onSearchTextChange: (String) -> Unit
) {
    JusicoolTheme { colors, _ ->
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            SearchBox(
                searchTextState = uiState.searchTextState,
                popularKeyword = uiState.popularKeyword,
                onSearchTextChange = onSearchTextChange,
                onArrowClick = popUpBackStack,
                onSearchClick = { searchStock(it) }
            )

            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = colors.gray100,
            )

            Spacer(Modifier.height(12.dp))

            RecentSearchSection(data = uiState.resentSearchTagData)

            Spacer(Modifier.height(24.dp))

            PopularKeywordSection(data = uiState.popularKeywordData)
        }
    }
}

@Preview(showBackground = true,backgroundColor = 0xFFFFFF)
@Composable
private fun StockSearchScreenPreview() {
    StockSearchScreen(
        onSearchTextChange = {},
        popUpBackStack = {},
        uiState = StockSearchUiState(
            popularKeywordData = persistentListOf(
                "삼성전자" to 12.1,
                "SK하이닉스" to 10.2,
                "네이버" to 9.3,
                "카카오" to 10.3,
            ),
            isLoading = false,
            searchTextState = "",
            popularKeyword = "삼성전자",
            resentSearchTagData = persistentListOf(
                StockSearchTagData(
                    stockName = "삼성전자",
                    stockChangeRate = 12.1,
                    onClearClick = {},
                ),
                StockSearchTagData(
                    stockName = "SK하이닉스",
                    stockChangeRate = 10.2,
                    onClearClick = {},
                ),
                StockSearchTagData(
                    stockName = "네이버",
                    stockChangeRate = 9.3,
                    onClearClick = {},
                ),
                StockSearchTagData(
                    stockName = "카카오",
                    stockChangeRate = 10.3,
                    onClearClick = {},
                )
            ),
            errorMessage = null,
        ),
        searchStock = {},
    )
}

data class StockSearchTagData(
    val stockName: String,
    val stockChangeRate: Double,
    val onClearClick: () -> Unit,
)

@Composable
private fun RecentSearchSection(data: PersistentList<StockSearchTagData>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(data, key = { _, item -> item.stockName }) { _, item ->
            RecentSearchTag(
                stockName = item.stockName,
                stockChangeRate = item.stockChangeRate,
                onClearClick = item.onClearClick
            )
        }
    }
}


@Composable
private fun PopularKeywordSection(
    data: PersistentList<Pair<String, Double>>,
) {
    JusicoolTheme { _, typography ->
        Row {
            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "인기 검색어",
                style = typography.bodyMedium,
            )
        }

        Spacer(Modifier.height(11.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(data, key = { _, item -> item.first }) { index, item ->
                SearchKeywordRow(
                    order = index + 1,
                    keyword = item.first,
                    changeRate = item.second,
                )
            }
        }
    }
}


@Composable
private fun SearchBox(
    modifier: Modifier = Modifier,
    searchTextState: String,
    popularKeyword: String,
    onSearchTextChange: (String) -> Unit,
    onArrowClick: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ClarityArrowLineIcon(
            modifier = Modifier
                .size(24.dp)
                .JusicoolClickable(onClick = onArrowClick),
        )

        TransparentTextField(
            modifier = Modifier.fillMaxWidth(),
            textState = searchTextState,
            onTextChange = onSearchTextChange,
            placeHolder = "'${popularKeyword}'를 검색해보세요",
        )
    }
}

@Composable
private fun SearchKeywordRow(
    modifier: Modifier = Modifier,
    order: Int,
    keyword: String,
    changeRate: Double,
) {
    val isPlus = changeRate > 0

    JusicoolTheme { colors, typography ->
        Row(
            modifier = modifier
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = "$order",
                    style = typography.bodyMedium,
                )

                Spacer(Modifier.width(50.dp))

                Text(
                    text = keyword,
                    style = typography.bodySmall,
                )
            }

            Text(
                text = FormatPercent.format(changeRate),
                style = typography.bodySmall,
                color = if (isPlus) colors.error else colors.main,
            )
        }
    }
}