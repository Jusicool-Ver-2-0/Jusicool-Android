package com.meister.investmentsearch.viewModel

import com.meister.investmentsearch.view.InvestmentSearchTagData
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class InvestmentSearchUiState(
    val isLoading: Boolean = true,
    val popularKeyword: String = "",
    val resentSearchTagData: PersistentList<InvestmentSearchTagData> = persistentListOf(),
    val popularKeywordData: PersistentList<Pair<String, Double>> = persistentListOf(),
    val errorMessage: String? = null,
)