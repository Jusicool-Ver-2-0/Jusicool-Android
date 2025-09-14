package com.jusicool.usecase.market

import android.util.Log
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.repository.MarketRepository
import com.jusicool.usecase.crypto.GetCurrentCryptoPriceUseCase
import com.jusicool.usecase.koreaInvestment.GetCurrentStockPriceUseCase
import com.jusicool.usecase.koreaInvestment.ObserveStockPriceWithFallbackUseCase
import com.jusicool.utils.isStockMarketOpen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import javax.inject.Inject

private const val TAG = "GetMarketListUseCase"

class GetMarketListWithCurrentPriceUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val getCurrentStockPriceUseCase: GetCurrentStockPriceUseCase,
    private val getCurrentCryptoPriceUseCase: GetCurrentCryptoPriceUseCase,
    private val observeStockPriceWithFallbackUseCase: ObserveStockPriceWithFallbackUseCase,
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

                    // --- 코드 불일치 로그 ---
                    val stockMarketCodes = stockMarkets.map { it.market }
                    val stockPriceCodes = stockPrices.map { it.market }
                    val missingStockCodes = stockMarketCodes - stockPriceCodes.toSet()
                    if (missingStockCodes.isNotEmpty()) {
                        Log.d(TAG, "⚠️ Stock market code mismatch: missing in price API = $missingStockCodes $stockMarketCodes $stockPriceCodes")
                    }

                    val cryptoMarketCodes = cryptoMarkets.map { it.market }
                    val cryptoPriceCodes = cryptoPrices.map { it.market }
                    val missingCryptoCodes = cryptoMarketCodes - cryptoPriceCodes.toSet()
                    if (missingCryptoCodes.isNotEmpty()) {
                        Log.d(TAG, "⚠️ Crypto market code mismatch: missing in price API = $missingCryptoCodes")
                    }

                    val stockWithPrice = stockPrices.mapNotNull { marketData ->
                        val market = stockMarkets.find { it.market == marketData.market }
                        if (market == null) {
                            Log.d(TAG, "❌ Stock mapping failed for ${marketData.market}")
                            null
                        } else {
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
                    }

                    val cryptoWithPrice = cryptoPrices.mapNotNull { marketData ->
                        val market = cryptoMarkets.find { it.market == marketData.market }
                        if (market == null) {
                            Log.d(TAG, "❌ Crypto mapping failed for ${marketData.market}")
                            null
                        } else {
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
                    }

                    marketList.mapNotNull { market ->
                        (stockWithPrice + cryptoWithPrice).find { it.market == market.market }
                    }
                }
            }
    }

    private fun getStockPriceFlow(stockMarkets: List<String>): Flow<List<AssetsCurrentPrice>> {
        if (stockMarkets.isEmpty()) return flowOf(emptyList())

        return if (isStockMarketOpen()) {
            observeStockPriceWithFallbackUseCase(stockMarkets)
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
}
