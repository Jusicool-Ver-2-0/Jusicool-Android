package com.meister.investmentsearch.viewModel

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
            combine(_pagesFlow, _currentPage) { pages, currentPage ->
                pages to currentPage
            }
                .flatMapLatest { (pages, currentPage) ->
                    if (pages.isEmpty()) return@flatMapLatest flowOf(emptyList<List<RecommendMarketWithPrice>>())

                    // 현재 페이지 주변 페이지만 선택
                    val targetPages = listOf(currentPage - 1, currentPage, currentPage + 1)
                        .filter { it in 1..pages.size }

                    val marketsToUpdate: List<RecommendMarketWithPrice> =
                        targetPages.flatMap { pageIndex ->
                            pages[pageIndex - 1]
                        }

                    val stockMarkets = marketsToUpdate.filter { it.marketType == MarketType.STOCK }
                        .map { it.market }
                    val cryptoMarkets = marketsToUpdate.filter { it.marketType == MarketType.CRYPTO }
                        .map { it.market }

                    combine(
                        getStockPriceFlow(stockMarkets),
                        getCryptoPriceFlow(cryptoMarkets)
                    ) { stockPrices, cryptoPrices ->
                        val updatedMarkets: List<RecommendMarketWithPrice> = (stockPrices + cryptoPrices).mapNotNull { priceData ->
                            marketsToUpdate.find { it.market == priceData.market }?.copy(
                                currentPrice = priceData.currentPrice,
                                profitRate = priceData.priceDifferenceRate
                            )
                        }

                        // 기존 pages를 복사하고 업데이트된 markets만 교체
                        pages.mapIndexed { index, page ->
                            if (index in targetPages.map { it - 1 }) {
                                page.map { market ->
                                    updatedMarkets.find { it.market == market.market } ?: market
                                }
                            } else page
                        }
                    }
                }
                .catch { e ->
                    Logger.e("ChartListViewModel", "❌ Error updating prices", e)
                }
                .collect { updatedPages ->
                    _pagesFlow.value = updatedPages
                    _uiState.update {
                        it.copy(
                            chartListData = updatedPages.flatten().toPersistentList()
                        )
                    }
                }
        }
    }

    internal fun loadNextPage() {
        if (_isLastPage) return

        viewModelScope.launch {
            Logger.d("ChartListViewModel", "📥 Loading page ${_currentPage.value}")
            getMarketListUseCase(currentPage = _currentPage.value, pageSize = PAGE_SIZE)
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { e ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = e.message)
                    }
                }
                .collect { newList ->
                    val mapped = newList.map {
                        RecommendMarketWithPrice(
                            id = it.id,
                            market = it.market,
                            marketType = it.marketType,
                            koreanName = it.koreanName,
                            englishName = it.englishName,
                            logoUrl = null,
                            currentPrice = 0.0,
                            profitRate = 0.0,
                        )
                    }

                    _pagesFlow.value = _pagesFlow.value + listOf(mapped)
                    _uiState.update {
                        it.copy(
                            isInitialLoad = false,
                            isLoading = false,
                            chartListData = _pagesFlow.value.flatten().toPersistentList()
                        )
                    }
                }
        }
    }

    private fun getStockPriceFlow(stockMarkets: List<String>): Flow<List<AssetsCurrentPrice>> {
        if (stockMarkets.isEmpty()) return flowOf(emptyList())
        return if (isStockMarketOpen()) {
            observeRealtimeStockPriceUseCase(stockMarkets)
        } else {
            getCurrentStockPriceUseCase(stockMarkets)
        }
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
        _currentPage.value = page
    }
}
