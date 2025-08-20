package com.jusicool.chart.viewModel.uiState

import com.jusicool.entity.price.MinuteCandleEntity

sealed interface GetMinuteCandleUiState {
    object Loading : GetMinuteCandleUiState
    data class Success(val chart: List<MinuteCandleEntity>): GetMinuteCandleUiState
    data class Error(val message: String) : GetMinuteCandleUiState
}