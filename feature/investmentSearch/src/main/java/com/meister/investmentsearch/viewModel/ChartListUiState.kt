package com.meister.investmentsearch.viewModel

import com.jusicool.entity.market.RecommendMarketWithPrice
import com.meister.investmentsearch.view.InvestmentSearchTagData
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ChartListUiState(
    val isLoading: Boolean = true,
    val isPaging: Boolean = false,
    val resentSearchTagData: PersistentList<InvestmentSearchTagData> = persistentListOf(),
    val chartListData: PersistentList<RecommendMarketWithPrice> = persistentListOf(),
    val errorMessage: String? = null,
)