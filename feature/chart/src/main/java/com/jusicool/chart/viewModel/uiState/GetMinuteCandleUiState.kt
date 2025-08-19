package com.jusicool.chart.viewModel.uiState

import com.jusicool.entity.price.MinuteCandleModel

sealed interface GetMinuteCandleUiState {
    object Loading : GetMinuteCandleUiState
    data class Success(val chart: List<MinuteCandleModel>): GetMinuteCandleUiState
    data class Error(val message: String) : GetMinuteCandleUiState
}