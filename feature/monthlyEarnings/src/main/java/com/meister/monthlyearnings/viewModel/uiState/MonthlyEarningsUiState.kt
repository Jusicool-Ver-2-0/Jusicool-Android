package com.meister.monthlyearnings.viewModel.uiState

import com.jusicool.entity.order.DailyRate
import com.jusicool.usecase.order.MonthlyRateGroup
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class MonthlyEarningsUiState(
    val isLoading: Boolean = true,
    val monthlyEarnings: Int = 0,
    val monthlyReturnRate: Double = 0.0,
    val totalHoldingAssetsData: PersistentList<DailyRate> = persistentListOf(),
    val stockHoldingsData: PersistentList<DailyRate> = persistentListOf(),
    val cryptoHoldingsData: PersistentList<DailyRate> = persistentListOf(),
    val errorMessage: String? = null,
) {
    companion object {
        fun success(data: MonthlyRateGroup): MonthlyEarningsUiState = MonthlyEarningsUiState(
            isLoading = false,
            monthlyEarnings = data.all.monthlyProfit(),
            monthlyReturnRate = data.all.monthlyRate,
            totalHoldingAssetsData = data.all.dailyRates.toPersistentList(),
            stockHoldingsData = data.stock.dailyRates.toPersistentList(),
            cryptoHoldingsData = data.crypto.dailyRates.toPersistentList(),
            errorMessage = null
        )
    }
}