package com.jusicool.usecase.market

import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.repository.MarketRepository
import com.jusicool.usecase.koreaInvestment.GetCurrentStockPriceUseCase
import com.jusicool.usecase.crypto.GetCurrentCryptoPriceUseCase
import com.jusicool.usecase.market.util.mergeWithPrices
import com.jusicool.utils.isValidCryptoMarketCode
import com.jusicool.utils.isValidStockMarketCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchMarketWithPriceUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val getCurrentStockPriceUseCase: GetCurrentStockPriceUseCase,
    private val getCurrentCryptoPriceUseCase: GetCurrentCryptoPriceUseCase,
) {
    operator fun invoke(query: String): Flow<List<RecommendMarketWithPrice>> =
        marketRepository.searchMarket(query)
            .flatMapLatest { marketList ->
                val stockMarkets = marketList.filter { it.market.isValidStockMarketCode() }
                val cryptoMarkets = marketList.filter { it.market.isValidCryptoMarketCode() }

                val stockFlow = getCurrentStockPriceUseCase(stockMarkets.map { it.market })
                val cryptoFlow = getCurrentCryptoPriceUseCase(cryptoMarkets.map { it.market })

                stockFlow.map {
                    stockMarkets.mergeWithPrices(it)
                }

                // combine(stockFlow, cryptoFlow) { stockPrices, cryptoPrices ->
                //     val stockResult = stockMarkets.mergeWithPrices(stockPrices)
                //     val cryptoResult = cryptoMarkets.mergeWithPrices(cryptoPrices)
                // stockResult + cryptoResult
                // }
            }
}
