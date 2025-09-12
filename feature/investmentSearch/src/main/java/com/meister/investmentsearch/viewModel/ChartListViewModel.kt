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
            _pagesFlow
                .flatMapLatest { pages ->
                    if (pages.isEmpty()) return@flatMapLatest flowOf(emptyList())

                    val allMarkets = pages.flatten()
                    val stockMarkets = allMarkets.filter { it.marketType == MarketType.STOCK }.map { it.market }
                    val cryptoMarkets = allMarkets.filter { it.marketType == MarketType.CRYPTO }.map { it.market }

                    combine(
                        getStockPriceFlow(stockMarkets),
                        getCryptoPriceFlow(cryptoMarkets)
                    ) { stockPrices, cryptoPrices ->
                        val updatedList = (stockPrices + cryptoPrices).mapNotNull { marketData ->
                            allMarkets.find { it.market == marketData.market }?.copy(
                                currentPrice = marketData.currentPrice,
                                profitRate = marketData.priceDifferenceRate,
                            )
                        }

                        pages.map { page ->
                            page.map { market ->
                                updatedList.find { it.market == market.market } ?: market
                            }
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
