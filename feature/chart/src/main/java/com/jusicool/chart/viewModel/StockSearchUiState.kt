package com.jusicool.chart.viewModel

import com.jusicool.chart.view.StockSearchTagData
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class StockSearchUiState(
    val isLoading: Boolean = false,
    val searchTextState: String = "",
    val popularKeyword: String = "",
    val resentSearchTagData: PersistentList<StockSearchTagData> = persistentListOf(),
    val popularKeywordData: PersistentList<Pair<String, Double>> = persistentListOf(),
    val errorMessage: String? = null,
)