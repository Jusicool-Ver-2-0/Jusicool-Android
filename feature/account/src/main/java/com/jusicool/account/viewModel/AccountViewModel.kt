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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AccountViewModel @Inject constructor(
    private val getAccountResponseUseCase: GetAccountResponseUseCase,
    private val getHoldingUseCase: GetHoldingWithCurrentPriceUseCase,
    private val getMonthOrderUseCase: GetMonthOrderUseCase
) : ViewModel() {

    private val _accountUiState = MutableStateFlow<GetAccountUiState>(GetAccountUiState.Loading)
    val accountUiState = _accountUiState.asStateFlow()

    private val _monthOrderUiState = MutableStateFlow<GetMonthOrderUiState>(GetMonthOrderUiState.Loading)
    val monthOrderUiState = _monthOrderUiState.asStateFlow()

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
            .catch { e ->
                Logger.e("AccountViewModel", "Error fetching holdings price at AccountViewModel.kt:53", e)
                emit(GetHoldingsPriceUiState.Error(e.message ?: "Unknown error"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = GetHoldingsPriceUiState.Loading
            )

    fun getAccount() = viewModelScope.launch {
        _accountUiState.value = GetAccountUiState.Loading
        getAccountResponseUseCase()
            .onSuccess { flow ->
                flow.catch { e ->
                    _accountUiState.value = GetAccountUiState.Error(e.message ?: "Unknown error")
                }.collect { account ->
                    _accountUiState.value = GetAccountUiState.Success(account)
                }
            }
            .onFailure { e ->
                _accountUiState.value = GetAccountUiState.Error(e.message ?: "Unknown error")
            }
    }

    fun getMonthOrder() = viewModelScope.launch {
        _monthOrderUiState.value = GetMonthOrderUiState.Loading
        getMonthOrderUseCase()
            .onSuccess { flow ->
                flow.catch { e ->
                    _monthOrderUiState.value = GetMonthOrderUiState.Error(e.message ?: "Unknown error")
                }.collect { order ->
                    _monthOrderUiState.value = GetMonthOrderUiState.Success(order)
                }
            }
            .onFailure { e ->
                _monthOrderUiState.value = GetMonthOrderUiState.Error(e.message ?: "Unknown error")
            }
    }
}

