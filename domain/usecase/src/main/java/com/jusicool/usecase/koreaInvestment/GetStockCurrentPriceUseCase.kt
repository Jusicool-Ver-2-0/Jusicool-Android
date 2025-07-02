package com.jusicool.usecase.koreaInvestment

import com.jusicool.entity.koreaInvestment.AssetsCurrentPrice
import com.jusicool.entity.market.MarketType
import com.jusicool.repository.KoreaInvestmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStockCurrentPriceUseCase @Inject constructor(
    private val koreaInvestmentRepository: KoreaInvestmentRepository,
    // TODO: crypto 추가 
) {
    operator fun invoke(
        markets: List<String>,
        marketType: MarketType,
    ): Flow<List<AssetsCurrentPrice>> = flow {
        val updatedMarkets = mutableListOf<AssetsCurrentPrice>()

        markets.forEach { market ->
            when (marketType) {
                MarketType.STOCK -> {
                    koreaInvestmentRepository.getStockCurrentPrice(
                        marketDivCode = "J",
                        stockCode = market
                    ).collect {
                        updatedMarkets.add(it)
                    }
                }

                MarketType.CRYPTO -> {

                }
            }
            emit(updatedMarkets)
        }
    }
}
