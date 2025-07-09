package com.meister.monthlyearnings.viewModel.uiState

import com.jusicool.entity.order.DailyRate
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MonthlyEarningsUiState(
    val isLoading: Boolean = true,
    val monthlyEarnings: Int = 0,
    val monthlyReturnRate: Double = 0.0,
    val totalHoldingAssetsData: PersistentList<DailyRate> = persistentListOf(),
    val stockHoldingsData: PersistentList<DailyRate> = persistentListOf(),
    val cryptoHoldingsData: PersistentList<DailyRate> = persistentListOf(),
    val errorMessage: String? = null,
)