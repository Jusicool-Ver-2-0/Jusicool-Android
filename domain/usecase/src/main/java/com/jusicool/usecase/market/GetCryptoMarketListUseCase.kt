package com.jusicool.usecase.market

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCryptoMarketListUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
) {
    operator fun invoke(): Flow<List<Market>> =
        marketRepository.getMarketList(MarketType.CRYPTO)
}