package com.jusicool.account.viewModel.uiState

import com.jusicool.entity.holding.HoldingModel

sealed interface GetHoldingUiState {
    object Loading : GetHoldingUiState
    data class Success(val account: List<HoldingModel>) : GetHoldingUiState
    data class Error(val message: String) : GetHoldingUiState
}