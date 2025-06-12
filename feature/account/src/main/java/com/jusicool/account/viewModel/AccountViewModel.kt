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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val getAccountResponseUseCase: GetAccountResponseUseCase,
    val getHoldingUseCase: GetHoldingResponseUseCase,
    val getCurrentCryptoPriceUseCase: GetCurrentCryptoPriceUseCase,
    val getMonthOrderUseCase: GetMonthOrderUseCase
) : ViewModel() {

    private val _accountUiState = MutableStateFlow<GetAccountUiState>(GetAccountUiState.Loading)
    val accountUiState = _accountUiState.asStateFlow()

    private val _holdingUiState = MutableStateFlow<GetHoldingUiState>(GetHoldingUiState.Loading)
    val holdingUiState = _holdingUiState.asStateFlow()

    private val _currentCryptoPriceUiState = MutableStateFlow<GetCurrentCryptoPriceUiState>(GetCurrentCryptoPriceUiState.Loading)
    val currentCryptoPriceUiState = _currentCryptoPriceUiState.asStateFlow()

    private val _monthOrderUiState = MutableStateFlow<GetMonthOrderUiState>(GetMonthOrderUiState.Loading)
    val monthOrderUiState = _monthOrderUiState.asStateFlow()

    private var cryptoPollingJob: Job? = null

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
        getHoldingUseCase()
            .onSuccess {
                it.catch {
                    Logger.e("AccountViewModel", "가지고 있는 코인&주식 정보 불러오기 실패: ${it.message}")
                    _holdingUiState.value = GetHoldingUiState.Error(it.message ?: "Unknown error")
                }
                it.collect { holding ->
                    Logger.d("AccountViewModel", "가지고 있는 코인&주식 정보 로드 성공: $holding")
                    _holdingUiState.value = GetHoldingUiState.Success(holding)

                    val marketValue = extractMarketValueFromHolding(holding)
                    startCryptoPricePolling(marketValue)
                }
            }
            .onFailure {
                Logger.e("AccountViewModel", "가지고 있는 코인&주식 정보 요청 실패: ${it.message}")
                _holdingUiState.value = GetHoldingUiState.Error(it.message ?: "Unknown error")
            }
    }

    private fun extractMarketValueFromHolding(holding: List<HoldingModel>): String {
        val cryptoMarketIds = holding
            .filter { it.marketType == "CRYPTO" }
            .mapNotNull { it.marketCode.takeIf { code -> code.matches(Regex("^[A-Z]{3,4}-[A-Z0-9]{2,10}$")) } }

        val markets = cryptoMarketIds.joinToString(separator = ",")
        Logger.d("AccountViewModel", "추출된 마켓 ID들: $markets")
        return markets
    }

    private fun startCryptoPricePolling(markets: String) {
        cryptoPollingJob?.cancel()

        cryptoPollingJob = viewModelScope.launch {
            if (markets.isBlank()) {
                Logger.d("AccountViewModel", "마켓 정보가 비어 있어 폴링을 시작할 수 없습니다.")
                _currentCryptoPriceUiState.value = GetCurrentCryptoPriceUiState.Blank
                return@launch
            }

            while (isActive) {
                getCurrentCryptoPrice(markets)
                delay(1000)
            }
        }
    }

    private suspend fun getCurrentCryptoPrice(markets: String) {
        getCurrentCryptoPriceUseCase(markets = markets)
            .onSuccess {
                it.catch {
                    Logger.e("AccountViewModel", "현재 코인 정보 불러오기 실패: ${it.message}")
                    _currentCryptoPriceUiState.value = GetCurrentCryptoPriceUiState.Error(it.message ?: "Unknown error")
                }
                it.collect { crypto ->
                    Logger.d("AccountViewModel", "현재 코인 정보 로드 성공: $crypto")
                    _currentCryptoPriceUiState.value = GetCurrentCryptoPriceUiState.Success(crypto)
                }
            }
            .onFailure {
                Logger.e("AccountViewModel", "현재 코인 정보 요청 실패: ${it.message}")
                _currentCryptoPriceUiState.value = GetCurrentCryptoPriceUiState.Error(it.message ?: "Unknown error")
            }
    }

    override fun onCleared() {
        super.onCleared()
        cryptoPollingJob?.cancel()
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
                    Logger.e("AccountViewModel", "한달 수익,주문 내역 불러오기 실패: ${order}")
                    _monthOrderUiState.value = GetMonthOrderUiState.Success(order)
                }
            }
            .onFailure {
                Logger.e("AccountViewModel", "한달 수익,주문 내역 불러오기 실패: ${it.message}")
                _monthOrderUiState.value = GetMonthOrderUiState.Error(it.message ?: "Unknown error")
            }
    }
}
