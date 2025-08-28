package com.jusicool.account.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.account.viewModel.uiState.GetAccountUiState
import com.jusicool.account.viewModel.uiState.GetHoldingsPriceUiState
import com.jusicool.account.viewModel.uiState.GetMonthOrderUiState
import com.jusicool.usecase.account.GetAccountResponseUseCase
import com.jusicool.usecase.holding.GetHoldingWithCurrentPriceUseCase
import com.jusicool.usecase.order.GetMonthOrderUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class AccountViewModel @Inject constructor(
    private val getAccountResponseUseCase: GetAccountResponseUseCase,
    private val getHoldingUseCase: GetHoldingWithCurrentPriceUseCase,
    private val getMonthOrderUseCase: GetMonthOrderUseCase
) : ViewModel() {

    val accountUiState: StateFlow<GetAccountUiState> =
        getAccountResponseUseCase()
            .map { GetAccountUiState.Success(it) }
            .catch {
                Logger.e(
                    tag = "AccountViewModel",
                    message = "Error fetching account",
                    throwable = it
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = GetAccountUiState.Loading
            )

    val monthOrderUiState: StateFlow<GetMonthOrderUiState> =
        getMonthOrderUseCase()
            .map { GetMonthOrderUiState.Success(it) }
            .catch {
                Logger.e(
                    tag = "AccountViewModel",
                    message = "Error fetching month order",
                    throwable = it
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = GetMonthOrderUiState.Loading
            )

    val currentAssetsPriceUiState: StateFlow<GetHoldingsPriceUiState> =
        getHoldingUseCase()
            .map { result ->
                if (result.stockHoldings.isEmpty() && result.cryptoHoldings.isEmpty()) {
                    GetHoldingsPriceUiState.Blank
                } else {
                    GetHoldingsPriceUiState.Success(
                        stockHoldings = result.stockHoldings.toPersistentList(),
                        cryptoHoldings = result.cryptoHoldings.toPersistentList(),
                    )
                }
            }
            .onEach { Logger.d("AccountViewModel", it.toString()) }
            .catch {
                Logger.e("AccountViewModel", "Error fetching holdings", it)
                emit(GetHoldingsPriceUiState.Error(it.message ?: "Unknown error"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = GetHoldingsPriceUiState.Loading
            )
}
