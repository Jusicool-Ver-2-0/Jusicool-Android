package com.jusicool.chart.viewModel

import com.jusicool.chart.view.StockSearchTagData
import kotlinx.collections.immutable.PersistentList

data class StockSearchUiState(
    val isLoading: Boolean = false,
    val searchTextState: String,
    val popularKeyword: String,
    val resentSearchTagData: PersistentList<StockSearchTagData>,
    val popularKeywordData: PersistentList<Pair<String, Double>>,
    val errorMessage: String? = null,
)