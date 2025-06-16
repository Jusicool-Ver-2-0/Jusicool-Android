package com.jusicool.chart.viewModel.uiState

import com.jusicool.entity.crypto.CurrentMinuteCandleModel

sealed interface GetCurrentMinuteCandleUiState {
    object Loading : GetCurrentMinuteCandleUiState
    object Blank : GetCurrentMinuteCandleUiState
    data class Success(val candles: List<CurrentMinuteCandleModel>) : GetCurrentMinuteCandleUiState
    data class Error(val message: String) : GetCurrentMinuteCandleUiState
}