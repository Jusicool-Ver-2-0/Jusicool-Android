package com.jusicool.usecase.market

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.repository.MarketRepository
import com.jusicool.usecase.koreaInvestment.GetCurrentStockPriceUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStockRecommendMarketListUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val getCurrentStockPriceUseCase: GetCurrentMarketListUseCase
) {
    operator fun invoke(): Flow<List<Market>> =
        marketRepository.getMarketList(MarketType.STOCK)
}