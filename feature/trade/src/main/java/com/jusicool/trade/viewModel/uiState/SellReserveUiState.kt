package com.jusicool.trade.viewModel.uiState

sealed interface SellReserveUiState {
    object Loading : SellReserveUiState
    object Success : SellReserveUiState
    data class Error(val message: String) : SellReserveUiState
}