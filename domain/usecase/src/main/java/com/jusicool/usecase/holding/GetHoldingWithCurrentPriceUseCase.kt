package com.jusicool.usecase.holding

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.entity.koreaInvestment.AssetsCurrentPrice
import com.jusicool.entity.price.HoldingWithCurrentPrice
import com.jusicool.usecase.crypto.GetCurrentCryptoPriceUseCase
import com.jusicool.usecase.koreaInvestment.GetCurrentStockPriceUseCase
import com.jusicool.usecase.koreaInvestment.ObserveRealtimeStockPriceUseCase
import com.jusicool.utils.isStockMarketOpen
import com.jusicool.utils.tickerFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetHoldingWithCurrentPriceUseCase @Inject constructor(
    private val getHoldingResponseUseCase: GetHoldingResponseUseCase,
    private val getCurrentStockPriceUseCase: GetCurrentStockPriceUseCase,
    private val getCurrentCryptoPriceUseCase: GetCurrentCryptoPriceUseCase,
    private val observeRealtimeStockPriceUseCase: ObserveRealtimeStockPriceUseCase
) {
    operator fun invoke(): Flow<HoldingWithCurrentPriceResult> {
        return getHoldingResponseUseCase()
            .flatMapLatest { holdingType ->

                val stockMarkets = holdingType.stockHoldings.map { it.market.market }
                val cryptoMarkets = holdingType.cryptoHoldings.map { it.market.market }

                val stockPriceFlow = getStockPriceFlow(stockMarkets)
                val cryptoPriceFlow = getCryptoPriceFlow(cryptoMarkets)

                combine(stockPriceFlow, cryptoPriceFlow) { stockPrices, cryptoPrices ->

                    val stockWithPrice = holdingType.stockHoldings.map { holding ->
                        HoldingWithCurrentPrice(
                            id = holding.id,
                            market = holding.market,
                            purchasePrice = holding.price,
                            quantity = holding.quantity,
                            currentPrice = findStockPrice(holding.market.market, stockPrices)
                        )
                    }

                    val cryptoWithPrice = holdingType.cryptoHoldings.map { holding ->
                        HoldingWithCurrentPrice(
                            id = holding.id,
                            market = holding.market,
                            purchasePrice = holding.price,
                            quantity = holding.quantity,
                            currentPrice = findCryptoPrice(holding.market.market, cryptoPrices)
                        )
                    }

                    HoldingWithCurrentPriceResult(
                        stockHoldings = stockWithPrice,
                        cryptoHoldings = cryptoWithPrice
                    )
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

    private fun getCryptoPriceFlow(cryptoMarkets: List<String>): Flow<List<CurrentCryptoPriceModel>> {
        if (cryptoMarkets.isEmpty()) return flowOf(emptyList())

        return tickerFlow(200)
            .flatMapLatest {
                getCurrentCryptoPriceUseCase(cryptoMarkets)
            }
    }

    private fun findStockPrice(marketCode: String, stockPrices: List<AssetsCurrentPrice>): Double {
        return stockPrices.find { it.market == marketCode }?.currentPrice?.toDouble() ?: 0.0
    }

    private fun findCryptoPrice(
        marketCode: String,
        cryptoPrices: List<CurrentCryptoPriceModel>
    ): Double {
        return cryptoPrices.find { it.market == marketCode }?.tradePrice ?: 0.0
    }
}


data class HoldingWithCurrentPriceResult(
    val stockHoldings: List<HoldingWithCurrentPrice>,
    val cryptoHoldings: List<HoldingWithCurrentPrice>,
)

fun List<HoldingWithCurrentPrice>.totalInvestment() = sumOf { it.purchasePrice * it.quantity }
fun List<HoldingWithCurrentPrice>.totalCurrentValue() = sumOf { it.totalValue() }