package com.jusicool.account.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.account.viewModel.uiState.GetAccountUiState
import com.jusicool.account.viewModel.uiState.GetCurrentCryptoPriceUiState
import com.jusicool.account.viewModel.uiState.GetHoldingUiState
import com.jusicool.account.viewModel.uiState.GetMonthOrderUiState
import com.jusicool.entity.holding.HoldingModel
import com.jusicool.usecase.account.GetAccountResponseUseCase
import com.jusicool.usecase.crypto.GetCurrentCryptoPriceUseCase
import com.jusicool.usecase.holding.GetHoldingResponseUseCase
import com.jusicool.usecase.holding.HoldingType
import com.jusicool.usecase.order.GetMonthOrderUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountResponseUseCase: GetAccountResponseUseCase,
    private val getHoldingUseCase: GetHoldingResponseUseCase,
    private val getCurrentCryptoPriceUseCase: GetCurrentCryptoPriceUseCase,
    private val getMonthOrderUseCase: GetMonthOrderUseCase
) : ViewModel() {

    private val _accountUiState = MutableStateFlow<GetAccountUiState>(GetAccountUiState.Loading)
    val accountUiState = _accountUiState.asStateFlow()

    private val _holdingUiState = MutableStateFlow<GetHoldingUiState>(GetHoldingUiState.Loading)
    val holdingUiState = _holdingUiState.asStateFlow()

    private val _monthOrderUiState = MutableStateFlow<GetMonthOrderUiState>(GetMonthOrderUiState.Loading)
    val monthOrderUiState = _monthOrderUiState.asStateFlow()

    // markets 문자열 (ex: "KRW-BTC,KRW-ETH")
    private val _markets = MutableStateFlow<String?>(null)
    // holdings 목록 (코인 보유 리스트)
    private val _cryptoHoldings = MutableStateFlow<List<HoldingModel>>(emptyList())

    val currentCryptoPriceUiState: StateFlow<GetCurrentCryptoPriceUiState> =
        combine(
            _markets.filterNotNull().filter { it.isNotBlank() },
            _cryptoHoldings
        ) { markets, holdings -> markets to holdings }
            .flatMapLatest { (markets, holdings) ->
                flow {
                    while (true) {
                        try {
                            val data = getCurrentCryptoPriceUseCase(markets, holdings)
                            emit(GetCurrentCryptoPriceUiState.Success(data))
                        } catch (e: Exception) {
                            emit(GetCurrentCryptoPriceUiState.Error(e.message ?: "Unknown error"))
                        }
                        delay(1000)
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = GetCurrentCryptoPriceUiState.Loading
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

    fun getHolding() = viewModelScope.launch {
        _holdingUiState.value = GetHoldingUiState.Loading

        getHoldingUseCase().fold(
            onSuccess = { holdingType ->
                // 주식은 일단 로그만 찍고
                launch {
                    holdingType.stockHoldings
                        .catch { e ->
                            _holdingUiState.value = GetHoldingUiState.Error(e.message ?: "Unknown error")
                        }
                        .collect { stockList ->
                            // 필요하면 주식 상태도 관리 가능
                        }
                }
                // 코인 보유 목록 가져와서 상태 업데이트 및 markets 추출
                launch {
                    holdingType.cryptoHoldings
                        .catch { e ->
                            _holdingUiState.value = GetHoldingUiState.Error(e.message ?: "Unknown error")
                        }
                        .collect { cryptoList ->
                            _holdingUiState.value = GetHoldingUiState.Success(cryptoList)
                            _cryptoHoldings.value = cryptoList
                            _markets.value = extractMarketValueFromHolding(cryptoList)
                        }
                }
            },
            onFailure = { e ->
                _holdingUiState.value = GetHoldingUiState.Error(e.message ?: "Unknown error")
            }
        )
    }

    private fun extractMarketValueFromHolding(holding: List<HoldingModel>): String {
        val cryptoMarketIds = holding
            .filter { it.marketType == "CRYPTO" }
            .mapNotNull { it.marketCode.takeIf { code -> code.matches(Regex("^[A-Z]{3,4}-[A-Z0-9]{2,10}$")) } }

        return cryptoMarketIds.joinToString(separator = ",")
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

