package com.jusicool.trade.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.order.BuyRequestModel
import com.jusicool.entity.order.BuyReserveModel
import com.jusicool.entity.order.SellRequestModel
import com.jusicool.entity.order.SellReserveModel
import com.jusicool.trade.viewModel.uiState.BuyReserveUiState
import com.jusicool.trade.viewModel.uiState.BuyUiState
import com.jusicool.trade.viewModel.uiState.SellReserveUiState
import com.jusicool.trade.viewModel.uiState.SellUiState
import com.jusicool.usecase.order.PostBuyReserveUseCase
import com.jusicool.usecase.order.PostBuyUseCase
import com.jusicool.usecase.order.PostSellReserveUseCase
import com.jusicool.usecase.order.PostSellUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TradeViewModel @Inject constructor(
    private val postBuyUseCase: PostBuyUseCase,
    private val postSellUseCase: PostSellUseCase,
    private val postBuyReserveUseCase: PostBuyReserveUseCase,
    private val postSellReserveUseCase: PostSellReserveUseCase
): ViewModel() {

    private val _buyUiState = MutableStateFlow<BuyUiState>(BuyUiState.Loading)
    internal val buyUiState = _buyUiState.asStateFlow()

    private val _sellUiState = MutableStateFlow<SellUiState>(SellUiState.Loading)
    internal val sellUiState = _sellUiState.asStateFlow()

    private val _buyReserveUiState = MutableStateFlow<BuyReserveUiState>(BuyReserveUiState.Loading)
    internal val buyReserveUiState = _buyReserveUiState.asStateFlow()

    private val _sellReserveUiState = MutableStateFlow<SellReserveUiState>(SellReserveUiState.Loading)
    internal val sellReserveUiState = _sellReserveUiState.asStateFlow()

    private val _quantity = MutableStateFlow("")
    internal val quantity = _quantity.asStateFlow()

    private val _price = MutableStateFlow("")
    internal val price = _price.asStateFlow()

    internal fun onQuantityChange(value: String) {
        _quantity.value = value
    }

    internal fun onPriceChange(value: String) {
        _price.value = value
    }

    internal fun onBuyClick(marketCode: String) {
        val quantityValue = quantity.value

        buy(marketCode, quantityValue)
    }

    private fun buy(marketCode: String, quantity: String) = viewModelScope.launch {
        _buyUiState.value = BuyUiState.Loading
        postBuyUseCase(marketCode, BuyRequestModel(quantity.toInt()))
            .onSuccess {
                it.catch { e ->
                    Logger.e("TradeViewModel", "구매 실패: ${e.message}")
                    _buyUiState.value = BuyUiState.Error(e.message ?: "Unknown error")
                }.collect { price ->
                    Logger.d("TradeViewModel", "구매 성공")
                    _buyUiState.value = BuyUiState.Success(price)
                }
            }
            .onFailure {
                Logger.e("TradeViewModel", "구매 실패: ${it.message}")
                _buyUiState.value = BuyUiState.Error(it.message ?: "Unknown error")
            }
    }

    internal fun onSellClick(marketCode: String) {
        val quantityValue = quantity.value

        sell(marketCode, quantityValue)
    }

    private fun sell(marketCode: String, quantity: String) = viewModelScope.launch {
        _sellUiState.value = SellUiState.Loading
        postSellUseCase(marketCode, SellRequestModel(quantity.toInt()))
            .onSuccess {
                it.catch { e ->
                    Logger.e("TradeViewModel", "판매 실패: ${e.message}")
                    _sellUiState.value = SellUiState.Error(e.message ?: "Unknown error")
                }.collect { price ->
                    Logger.d("TradeViewModel", "판매 성공")
                    _sellUiState.value = SellUiState.Success(price)
                }
            }
            .onFailure {
                Logger.e("TradeViewModel", "판매 실패: ${it.message}")
                _sellUiState.value = SellUiState.Error(it.message ?: "Unknown error")
            }
    }

    internal fun onBuyReserveClick(marketCode: String,) {
        val quantityValue = quantity.value
        val priceValue = price.value

        buyReserve(marketCode,quantityValue, priceValue)
    }

    private fun buyReserve(marketCode: String, quantity: String, price: String) = viewModelScope.launch {
        _buyReserveUiState.value = BuyReserveUiState.Loading
        postBuyReserveUseCase(marketCode, BuyReserveModel( quantity.toInt(), price.toInt()))
            .onSuccess {
                it.catch { e ->
                    Logger.e("TradeViewModel", "구매 예약 실패: ${e.message}")
                    _buyReserveUiState.value = BuyReserveUiState.Error(e.message ?: "Unknown error")
                }.collect {
                    Logger.d("TradeViewModel", "구매 예약 성공")
                    _buyReserveUiState.value = BuyReserveUiState.Success
                }
            }
            .onFailure {
                Logger.e("TradeViewModel", "구매 예약 실패: ${it.message}")
                _buyReserveUiState.value = BuyReserveUiState.Error(it.message ?: "Unknown error")
            }
    }

    internal fun onSellReserveClick(marketCode: String,) {
        val quantityValue = quantity.value
        val priceValue = price.value

        sellReserve(marketCode,quantityValue, priceValue)
    }

    private fun sellReserve(marketCode: String, quantity: String, price: String) = viewModelScope.launch {
        _sellReserveUiState.value = SellReserveUiState.Loading
        postSellReserveUseCase(marketCode, SellReserveModel(quantity.toInt(), price.toInt()))
            .onSuccess {
                it.catch { e ->
                    Logger.e("TradeViewModel", "판매 예약 실패: ${e.message}")
                    _sellReserveUiState.value = SellReserveUiState.Error(e.message ?: "Unknown error")
                }.collect {

                    Logger.d("TradeViewModel", "판매 예약 성공")
                    _sellReserveUiState.value = SellReserveUiState.Success
                }
            }
            .onFailure {
                Logger.e("TradeViewModel", "판매 예약 실패: ${it.message}")
                _sellReserveUiState.value = SellReserveUiState.Error(it.message ?: "Unknown error")
            }
    }
}