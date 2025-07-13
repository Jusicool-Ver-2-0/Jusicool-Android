package com.meister.investmentsearch.view

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
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.TransparentTextField
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.formatPercent
import com.meister.investmentsearch.component.RecentSearchTag
import com.meister.investmentsearch.viewModel.InvestmentSearchUiState
import com.meister.investmentsearch.viewModel.InvestmentSearchViewModel
import com.jusicool.design_system.icon.LeftClarityArrowLineIcon
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun InvestmentSearchRoute(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    viewModel: InvestmentSearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchTextState by viewModel.searchQuery.collectAsStateWithLifecycle()

    InvestmentSearchScreen(
        modifier = modifier,
        searchTextState = "",
        uiState = uiState,
        popBackStack = popBackStack,
        onSearchTextChange = viewModel::onSearchTextChange,
    )
}

@Composable
private fun InvestmentSearchScreen(
    modifier: Modifier = Modifier,
    searchTextState: String,
    uiState: InvestmentSearchUiState,
    popBackStack: () -> Unit,
    onSearchTextChange: (String) -> Unit,
) {
    JusicoolTheme { colors, _ ->
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            SearchBox(
                popularKeyword = uiState.popularKeyword,
                searchTextState = searchTextState,
                onSearchTextChange = onSearchTextChange,
                onArrowClick = popBackStack,
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun InvestmentSearchScreenPreview() {
    InvestmentSearchScreen(
        searchTextState = "",
        onSearchTextChange = {},
        popBackStack = {},
        uiState = InvestmentSearchUiState(
            popularKeywordData = persistentListOf(
                "삼성전자" to 12.1,
                "SK하이닉스" to 10.2,
                "네이버" to 9.3,
                "카카오" to 10.3,
            ),
            isLoading = false,
            popularKeyword = "삼성전자",
            resentSearchTagData = persistentListOf(
                InvestmentSearchTagData(
                    investmentName = "삼성전자",
                    investmentChangeRate = 12.1,
                    onClearClick = {},
                ),
                InvestmentSearchTagData(
                    investmentName = "SK하이닉스",
                    investmentChangeRate = 10.2,
                    onClearClick = {},
                ),
                InvestmentSearchTagData(
                    investmentName = "네이버",
                    investmentChangeRate = 9.3,
                    onClearClick = {},
                ),
                InvestmentSearchTagData(
                    investmentName = "카카오",
                    investmentChangeRate = 10.3,
                    onClearClick = {},
                )
            ),
            errorMessage = null,
        ),
    )
}

data class InvestmentSearchTagData(
    val investmentName: String,
    val investmentChangeRate: Double,
    val onClearClick: () -> Unit,
)

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
) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LeftClarityArrowLineIcon(
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

            Spacer(Modifier.weight(1f))

            Text(
                text = changeRate.formatPercent(),
                style = typography.bodySmall,
                color = if (isPlus) colors.error else colors.main,
            )
        }
    }
}