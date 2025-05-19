package com.jusicool.chart.viewModel

import com.jusicool.model.chart.ChartResponse

internal sealed class ChartUiState {
    object Loading : ChartUiState()
    data class Success(val chart: List<ChartResponse>) : ChartUiState()
    data class Error(val message: String) : ChartUiState()
}