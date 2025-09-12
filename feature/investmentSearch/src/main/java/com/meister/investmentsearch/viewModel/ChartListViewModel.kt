package com.meister.investmentsearch.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.usecase.crypto.GetCurrentCryptoPriceUseCase
import com.jusicool.usecase.koreaInvestment.GetCurrentStockPriceUseCase
import com.jusicool.usecase.koreaInvestment.ObserveRealtimeStockPriceUseCase
import com.jusicool.usecase.market.GetMarketListUseCase
import com.jusicool.utils.Logger
import com.jusicool.utils.isStockMarketOpen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ChartListViewModel @Inject constructor(
    private val getCurrentStockPriceUseCase: GetCurrentStockPriceUseCase,
    private val getCurrentCryptoPriceUseCase: GetCurrentCryptoPriceUseCase,
    private val observeRealtimeStockPriceUseCase: ObserveRealtimeStockPriceUseCase,
    private val getMarketListUseCase: GetMarketListUseCase,
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 40
    }

    private var _isLastPage = false

    private val _pagesFlow = MutableStateFlow<List<List<RecommendMarketWithPrice>>>(emptyList())
    private val _currentPage = MutableStateFlow(1)

    private val _uiState = MutableStateFlow(ChartListUiState())
    val uiState: StateFlow<ChartListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _currentPage
                .flatMapLatest { page ->
                    val needLoad = page >= _pagesFlow.value.size
                    if (needLoad) {
                        getMarketListUseCase(currentPage = page, pageSize = PAGE_SIZE)
                            .onStart { _uiState.update { it.copy(isLoading = true) } }
                            .catch { e ->
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = e.message
                                    )
                                }
                            }
                            .flatMapLatest { newList ->
                                val mapped = newList.map { item ->
                                    RecommendMarketWithPrice(
                                        id = item.id,
                                        market = item.market,
                                        marketType = item.marketType,
                                        koreanName = item.koreanName,
                                        englishName = item.englishName,
                                        logoUrl = null,
                                        currentPrice = 0.0,
                                        profitRate = 0.0
                                    )
                                }
                                _pagesFlow.update { it + listOf(mapped) }

                                val pageIndexes = listOf(page - 2, page - 1, page)
                                    .filter { it in _pagesFlow.value.indices }
                                val neighborMarkets =
                                    pageIndexes.flatMap { idx -> _pagesFlow.value[idx] }

                                val stockMarkets = neighborMarkets
                                    .filter { it.marketType == MarketType.STOCK }
                                    .map { it.market }
                                val cryptoMarkets = neighborMarkets
                                    .filter { it.marketType == MarketType.CRYPTO }
                                    .map { it.market }

                                combine(
                                    getStockPriceFlow(stockMarkets),
                                    getCryptoPriceFlow(cryptoMarkets)
                                ) { stockPrices, cryptoPrices ->
                                    val updatedList =
                                        (stockPrices + cryptoPrices).mapNotNull { marketData ->
                                            neighborMarkets.find { it.market == marketData.market }
                                                ?.copy(
                                                    currentPrice = marketData.currentPrice,
                                                    profitRate = marketData.priceDifferenceRate
                                                )
                                        }

                                    _pagesFlow.value.mapIndexed { index, page ->
                                        if (index in pageIndexes) {
                                            page.map { market ->
                                                updatedList.find { it.market == market.market }
                                                    ?: market
                                            }
                                        } else page
                                    }
                                }
                            }
                    } else {
                        // 이미 로드된 페이지이면 아무 동작 안 함
                        flowOf(_pagesFlow.value)
                    }
                }
                .catch { e -> Logger.e("ChartListViewModel", "❌ Error in _currentPage flow", e) }
                .collect { updatedPages ->
                    _pagesFlow.value = updatedPages
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isInitialLoad = false,
                            chartListData = updatedPages.flatten().toPersistentList()
                        )
                    }
                }
        }
    }

    private fun getStockPriceFlow(stockMarkets: List<String>): Flow<List<AssetsCurrentPrice>> {
        if (stockMarkets.isEmpty()) return flowOf(emptyList())
        return if (isStockMarketOpen()) observeRealtimeStockPriceUseCase(stockMarkets)
        else getCurrentStockPriceUseCase(stockMarkets)
    }

    private fun getCryptoPriceFlow(cryptoMarkets: List<String>): Flow<List<AssetsCurrentPrice>> {
        if (cryptoMarkets.isEmpty()) return flowOf(emptyList())
        return flow {
            while (currentCoroutineContext().isActive) {
                emitAll(getCurrentCryptoPriceUseCase(cryptoMarkets))
                delay(500)
            }
        }.flowOn(Dispatchers.IO)
    }

    internal fun setCurrentPage(page: Int) {
        Log.d("setCurrentPage", page.toString())
        _currentPage.value = page
    }
}
