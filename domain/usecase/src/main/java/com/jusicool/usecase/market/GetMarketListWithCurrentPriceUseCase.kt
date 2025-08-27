package com.jusicool.usecase.market

import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.repository.MarketRepository
import com.jusicool.usecase.crypto.GetCurrentCryptoPriceUseCase
import com.jusicool.usecase.koreaInvestment.GetCurrentStockPriceUseCase
import com.jusicool.usecase.koreaInvestment.ObserveRealtimeStockPriceUseCase
import com.jusicool.utils.isStockMarketOpen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import javax.inject.Inject

class GetMarketListWithCurrentPriceUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val getCurrentStockPriceUseCase: GetCurrentStockPriceUseCase,
    private val getCurrentCryptoPriceUseCase: GetCurrentCryptoPriceUseCase,
    private val observeRealtimeStockPriceUseCase: ObserveRealtimeStockPriceUseCase
) {
    operator fun invoke(
        type: MarketType? = null,
        currentPage: Int,
        pageSize: Int,
    ): Flow<List<RecommendMarketWithPrice>> {
        return marketRepository.getMarketList(type = type, page = currentPage, size = pageSize)
            .flatMapLatest { marketList ->
                val stockMarkets = marketList.filter { it.isStock }
                val cryptoMarkets = marketList.filter { it.isCrypto }

                val stockPriceFlow = getStockPriceFlow(stockMarkets.map { it.market })
                val cryptoPriceFlow = getCryptoPriceFlow(cryptoMarkets.map { it.market })

                combine(stockPriceFlow, cryptoPriceFlow) { stockPrices, cryptoPrices ->
                    val stockWithPrice = stockPrices.map { marketData ->
                        val market = stockMarkets.find { it.market == marketData.market }!!

                        RecommendMarketWithPrice(
                            id = market.id,
                            market = marketData.market,
                            marketType = MarketType.STOCK,
                            koreanName = market.koreanName,
                            englishName = market.englishName,
                            logoUrl = null,
                            currentPrice = marketData.currentPrice,
                            profitRate = marketData.priceDifferenceRate,
                        )
                    }

                    val cryptoWithPrice = cryptoPrices.map { marketData ->
                        val market = cryptoMarkets.find { it.market == marketData.market }!!

                        RecommendMarketWithPrice(
                            id = market.id,
                            market = marketData.market,
                            marketType = MarketType.CRYPTO,
                            koreanName = market.koreanName,
                            englishName = market.englishName,
                            logoUrl = null,
                            currentPrice = marketData.currentPrice,
                            profitRate = marketData.priceDifferenceRate,
                        )
                    }

                    marketList.map { market -> // 정렬 로직
                        (stockWithPrice + cryptoWithPrice).find { it.market == market.market }!!
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

    private fun getCryptoPriceFlow(
        cryptoMarkets: List<String>
    ): Flow<List<AssetsCurrentPrice>> {
        if (cryptoMarkets.isEmpty()) return flowOf(emptyList())

        return flow {
            while (currentCoroutineContext().isActive) {
                getCurrentCryptoPriceUseCase(cryptoMarkets).collect { emit(it) }
                delay(500)
            }
        }.flowOn(Dispatchers.IO)
    }
}