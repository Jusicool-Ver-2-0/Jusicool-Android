package com.meister.assets.view.previewProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.meister.assets.viewModel.uiState.MonthlyIncomeUiState
import kotlinx.collections.immutable.persistentListOf

class MonthlyIncomeUiStateProvider : PreviewParameterProvider<MonthlyIncomeUiState> {
    override val values = sequenceOf(
        MonthlyIncomeUiState(
            isLoading = false,
            myMoney = 1_000_000,
            moneyChangeFromLastMonth = 50_000,
            availableOrderAmount = 200_000,
            investedAmount = 800_000,
            ownedStocks = persistentListOf(
                "삼성전자" to 100,
                "카카오" to 200,
                "네이버" to 150,
            ),
            errorMessage = null
        ),
        MonthlyIncomeUiState(
            isLoading = false,
            myMoney = 500_000,
            moneyChangeFromLastMonth = -30_000,
            availableOrderAmount = 50_000,
            investedAmount = 450_000,
            ownedStocks = persistentListOf(
                "LG에너지솔루션" to 80,
                "현대차" to 120
            ),
            errorMessage = null
        )
    )
}
