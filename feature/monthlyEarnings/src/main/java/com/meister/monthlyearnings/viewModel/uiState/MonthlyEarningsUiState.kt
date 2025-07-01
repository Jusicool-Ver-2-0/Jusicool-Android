package com.meister.monthlyearnings.viewModel.uiState

import com.meister.monthlyearnings.view.ChartItemData
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MonthlyEarningsUiState(
    val isLoading: Boolean = true,
    val monthlyEarnings: Int = 0,
    val monthlyReturnRate: Double = 0.0,
    val totalHoldingAssetsData: PersistentList<ChartItemData> = persistentListOf(),
    val stockHoldingsData: PersistentList<ChartItemData> = persistentListOf(),
    val cryptoHoldingsData: PersistentList<ChartItemData> = persistentListOf(),
    val errorMessage: String? = null,
)