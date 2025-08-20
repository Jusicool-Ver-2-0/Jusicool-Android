package com.jusicool.chart.viewModel.uiState

import com.jusicool.entity.price.MinuteCandleEntity

sealed interface GetCurrentMinuteCandleUiState {
    object Loading : GetCurrentMinuteCandleUiState
    object Blank : GetCurrentMinuteCandleUiState
    data class Success(val candles: List<MinuteCandleEntity>) : GetCurrentMinuteCandleUiState
    data class Error(val message: String) : GetCurrentMinuteCandleUiState
}