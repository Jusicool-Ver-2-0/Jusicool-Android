package com.jusicool.account.viewModel.uiState

import com.jusicool.entity.crypto.CurrentCryptoPriceModel

sealed interface GetCurrentCryptoPriceUiState {
    object Loading : GetCurrentCryptoPriceUiState
    object Blank: GetCurrentCryptoPriceUiState
    data class Success(val markets: List<CurrentCryptoPriceModel>) : GetCurrentCryptoPriceUiState
    data class Error(val message: String) : GetCurrentCryptoPriceUiState
}