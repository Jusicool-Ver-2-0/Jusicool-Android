package com.jusicool.account.viewModel.uiState

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.usecase.crypto.CurrentCryptoHoldingPrice

sealed interface GetCurrentCryptoPriceUiState {
    object Loading : GetCurrentCryptoPriceUiState
    object Blank: GetCurrentCryptoPriceUiState
    data class Success(val markets: List<CurrentCryptoHoldingPrice>) : GetCurrentCryptoPriceUiState
    data class Error(val message: String) : GetCurrentCryptoPriceUiState
}