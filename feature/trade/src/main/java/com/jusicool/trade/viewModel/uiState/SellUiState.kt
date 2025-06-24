package com.jusicool.trade.viewModel.uiState

import com.jusicool.entity.order.SellResponseModel

sealed interface SellUiState {
    object Loading : SellUiState
    data class Success(val price: SellResponseModel) : SellUiState
    data class Error(val message: String) : SellUiState
}