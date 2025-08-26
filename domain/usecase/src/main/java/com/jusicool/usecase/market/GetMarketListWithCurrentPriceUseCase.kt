package com.jusicool.usecase.market

import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMarketListWithCurrentPriceUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
) {
    operator fun invoke(
        type: MarketType? = null,
        currentPage: Int,
        pageSize: Int,
    ): Flow<List<RecommendMarketWithPrice>> = flow {
        marketRepository.getMarketList(type = type, page = 0, size = pageSize)
    }
}