package com.jusicool.account.viewModel.uiState

import com.jusicool.entity.price.HoldingWithCurrentPrice
import kotlinx.collections.immutable.PersistentList

sealed interface GetHoldingsPriceUiState {
    object Loading : GetHoldingsPriceUiState
    object Blank : GetHoldingsPriceUiState
    data class Success(
        val stockHoldings: PersistentList<HoldingWithCurrentPrice>,
        val cryptoHoldings: PersistentList<HoldingWithCurrentPrice>,
    ) : GetHoldingsPriceUiState

    data class Error(val message: String) : GetHoldingsPriceUiState
}