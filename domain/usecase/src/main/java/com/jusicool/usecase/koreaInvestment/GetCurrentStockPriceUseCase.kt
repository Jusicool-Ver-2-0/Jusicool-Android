package com.jusicool.usecase.koreaInvestment

import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.repository.KoreaInvestmentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentStockPriceUseCase @Inject constructor(
    private val koreaInvestmentRepository: KoreaInvestmentRepository,
) {
    operator fun invoke(
        markets: List<String>,
    ): Flow<List<AssetsCurrentPrice>> = flow {
        val updatedMarkets = mutableListOf<AssetsCurrentPrice>()

        markets.forEach { market ->
            koreaInvestmentRepository.getStockCurrentPrice(
                marketDivCode = "J",
                stockCode = market
            ).collect {
                updatedMarkets.add(it)
            }
            emit(updatedMarkets)
            delay(300)
        }
    }
}
