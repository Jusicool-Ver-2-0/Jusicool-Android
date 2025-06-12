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
import com.jusicool.usecase.order.GetMonthOrderUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _markets = MutableStateFlow<String?>(null)

    val currentCryptoPriceUiState: StateFlow<GetCurrentCryptoPriceUiState> =
        _markets
            .flatMapLatest { markets ->
                if (markets.isNullOrBlank()) {
                    flowOf(GetCurrentCryptoPriceUiState.Blank)
                } else {
                    flow {
                        while (true) {
                            emit(markets)
                            kotlinx.coroutines.delay(1000)
                        }
                    }.flatMapLatest { mkt ->
                        getCurrentCryptoPriceUseCase(mkt)
                            .getOrElse {
                                Logger.e("AccountViewModel", "현재 코인 가격 요청 실패: ${it.message}")
                                return@flatMapLatest flowOf(
                                    GetCurrentCryptoPriceUiState.Error(it.message ?: "Unknown error")
                                )
                            }
                            .catch {
                                Logger.e("AccountViewModel", "가격 로딩 중 에러: ${it.message}")
                                GetCurrentCryptoPriceUiState.Error(it.message ?: "Unknown error")
                            }
                            .map { crypto ->
                                GetCurrentCryptoPriceUiState.Success(crypto)
                            }
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = GetCurrentCryptoPriceUiState.Loading
            )

    fun getAccount() = viewModelScope.launch {
        _accountUiState.value = GetAccountUiState.Loading
        getAccountResponseUseCase()
            .onSuccess {
                it.catch {
                    Logger.e("AccountViewModel", "계정 정보 불러오기 실패: ${it.message}")
                    _accountUiState.value = GetAccountUiState.Error(it.message ?: "Unknown error")
                }
                it.collect { account ->
                    Logger.d("AccountViewModel", "계정 정보 로드 성공: $account")
                    _accountUiState.value = GetAccountUiState.Success(account)
                }
            }
            .onFailure {
                Logger.e("AccountViewModel", "계정 정보 요청 실패: ${it.message}")
                _accountUiState.value = GetAccountUiState.Error(it.message ?: "Unknown error")
            }
    }

    fun getHolding() = viewModelScope.launch {
        _holdingUiState.value = GetHoldingUiState.Loading

        getHoldingUseCase().fold(
            onSuccess = { holdingType ->
                launch {
                    holdingType.stockHoldings
                        .catch {
                            Logger.e("AccountViewModel", "주식 정보 로드 실패: ${it.message}")
                            _holdingUiState.value = GetHoldingUiState.Error(it.message ?: "Unknown error")
                        }
                        .collect { stockList ->
                            Logger.d("AccountViewModel", "주식 정보 로드 성공: $stockList")
                        }
                }

                launch {
                    holdingType.cryptoHoldings
                        .catch {
                            Logger.e("AccountViewModel", "코인 정보 로드 실패: ${it.message}")
                            _holdingUiState.value = GetHoldingUiState.Error(it.message ?: "Unknown error")
                        }
                        .collect { cryptoList ->
                            Logger.d("AccountViewModel", "코인 정보 로드 성공: $cryptoList")
                            _holdingUiState.value = GetHoldingUiState.Success(cryptoList)
                            val marketValue = extractMarketValueFromHolding(cryptoList)
                            _markets.value = marketValue
                        }
                }
            },
            onFailure = { throwable ->
                Logger.e("AccountViewModel", "가지고 있는 코인&주식 정보 요청 실패: ${throwable.message}")
                _holdingUiState.value = GetHoldingUiState.Error(throwable.message ?: "Unknown error")
            }
        )
    }

    private fun extractMarketValueFromHolding(holding: List<HoldingModel>): String {
        val cryptoMarketIds = holding
            .filter { it.marketType == "CRYPTO" }
            .mapNotNull { it.marketCode.takeIf { code -> code.matches(Regex("^[A-Z]{3,4}-[A-Z0-9]{2,10}$")) } }

        val markets = cryptoMarketIds.joinToString(separator = ",")
        Logger.d("AccountViewModel", "추출된 마켓 ID들: $markets")
        return markets
    }

    fun getMonthOrder() = viewModelScope.launch {
        _monthOrderUiState.value = GetMonthOrderUiState.Loading
        getMonthOrderUseCase()
            .onSuccess {
                it.catch {
                    Logger.e("AccountViewModel", "한달 수익,주문 내역 정보 불러오기 실패: ${it.message}")
                    _monthOrderUiState.value = GetMonthOrderUiState.Error(it.message ?: "Unknown error")
                }
                it.collect{ order ->
                    Logger.d("AccountViewModel", "한달 수익,주문 내역 불러오기 성공: $order")
                    _monthOrderUiState.value = GetMonthOrderUiState.Success(order)
                }
            }
            .onFailure {
                Logger.e("AccountViewModel", "한달 수익,주문 내역 불러오기 실패: ${it.message}")
                _monthOrderUiState.value = GetMonthOrderUiState.Error(it.message ?: "Unknown error")
            }
    }
}
