package com.meister.investmentsearch.viewModel

import com.meister.investmentsearch.view.ChartItemData
import com.meister.investmentsearch.view.InvestmentSearchTagData
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ChartListUiState(
    val isLoading: Boolean = false,
    val resentSearchTagData: PersistentList<InvestmentSearchTagData> = persistentListOf(),
    val chartListData: PersistentList<ChartItemData> = persistentListOf(),
    val errorMessage: String? = null,
)