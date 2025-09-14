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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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

    private val _currentPage = MutableStateFlow(1)
    private val _pagesFlow = MutableStateFlow<List<List<RecommendMarketWithPrice>>>(emptyList())

    private val _uiState = MutableStateFlow(ChartListUiState())
    val uiState: StateFlow<ChartListUiState> = _uiState

    init {
        observePages()
    }

    private fun observePages() {
        viewModelScope.launch {
            _currentPage
                .flatMapLatest { page -> loadPageFlow(page) }
                .catch { e -> Logger.e("ChartListViewModel", "❌ Error in page flow", e) }
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

    private fun loadPageFlow(page: Int): Flow<List<List<RecommendMarketWithPrice>>> {
        val needLoad = page >= _pagesFlow.value.size
        if (!needLoad) return flowOf(_pagesFlow.value)

        return getMarketListUseCase(currentPage = page, pageSize = PAGE_SIZE)
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .catch { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
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

                val newPages = _pagesFlow.value + listOf(mapped)
                val pageIndexes = listOf(page - 2, page - 1, page).filter { it in newPages.indices }
                val neighborMarkets = pageIndexes.flatMap { newPages[it] }

                val stockMarkets = neighborMarkets.filter { it.marketType == MarketType.STOCK }.map { it.market }
                val cryptoMarkets = neighborMarkets.filter { it.marketType == MarketType.CRYPTO }.map { it.market }

                combine(
                    getStockPriceFlow(stockMarkets),
                    getCurrentCryptoPriceUseCase(cryptoMarkets)
                ) { stockPrices, cryptoPrices ->
                    val updatedList = (stockPrices + cryptoPrices).mapNotNull { marketData ->
                        neighborMarkets.find { it.market == marketData.market }?.copy(
                            currentPrice = marketData.currentPrice,
                            profitRate = marketData.priceDifferenceRate
                        )
                    }

                    newPages.mapIndexed { index, pageList ->
                        if (index in pageIndexes) {
                            pageList.map { market ->
                                updatedList.find { it.market == market.market } ?: market
                            }
                        } else pageList
                    }
                }
            }
    }

    private fun getStockPriceFlow(stockMarkets: List<String>): Flow<List<AssetsCurrentPrice>> {
        if (stockMarkets.isEmpty()) return flowOf(emptyList())
        return if (isStockMarketOpen()) observeRealtimeStockPriceUseCase(stockMarkets)
        else getCurrentStockPriceUseCase(stockMarkets)
    }

    fun setCurrentPage(page: Int) {
        _currentPage.value = page
    }
}
