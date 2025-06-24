package com.jusicool.trade.viewModel.uiState

import com.jusicool.entity.order.BuyResponseModel

sealed interface BuyUiState {
    object Loading : BuyUiState
    data class Success(val price: BuyResponseModel) : BuyUiState
    data class Error(val message: String) : BuyUiState
}