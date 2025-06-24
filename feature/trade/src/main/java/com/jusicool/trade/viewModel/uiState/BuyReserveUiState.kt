package com.jusicool.trade.viewModel.uiState

import com.jusicool.entity.order.BuyResponseModel

sealed interface BuyReserveUiState {
    object Loading : BuyReserveUiState
    object Success : BuyReserveUiState
    data class Error(val message: String) : BuyReserveUiState
}