package com.jusicool.chart.viewModel

internal sealed class ChartUiState {
    object Loading : ChartUiState()
    class Success: ChartUiState()
    data class Error(val message: String) : ChartUiState()
}