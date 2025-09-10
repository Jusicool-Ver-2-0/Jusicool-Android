package com.jusicool.usecase.market

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarketListUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
) {
    operator fun invoke(
        type: MarketType? = null,
        currentPage: Int,
        pageSize: Int,
    ): Flow<List<Market>> =
        marketRepository.getMarketList(type = type, page = currentPage, size = pageSize)
}