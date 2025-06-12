package com.meister.investmentsearch.viewModel

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class InvestmentSearchUiState(
    val isLoading: Boolean = false,
    val searchTextState: String = "",
    val popularKeyword: String = "",
    val resentSearchTagData: PersistentList<com.meister.investmentsearch.view.InvestmentSearchTagData> = persistentListOf(),
    val popularKeywordData: PersistentList<Pair<String, Double>> = persistentListOf(),
    val errorMessage: String? = null,
)