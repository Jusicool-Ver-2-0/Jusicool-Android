package com.jusicool.usecase.market

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarketListUseCase @Inject constructor(
    private val marketRepository: MarketRepository
) {
    operator fun invoke(requestParam: MarketType): Flow<List<Market>> =
        marketRepository.getMarketList(requestParam = requestParam)
}