package com.jusicool.account.viewModel.uiState

import com.jusicool.entity.order.OrderModel

interface GetMonthOrderUiState {
    object Loading : GetMonthOrderUiState
    data class Success(val account: OrderModel) : GetMonthOrderUiState
    data class Error(val message: String) : GetMonthOrderUiState
}