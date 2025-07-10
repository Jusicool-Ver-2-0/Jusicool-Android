package com.jusicool.usecase.market

import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCryptoRecommendMarketListUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
) {
    operator fun invoke(): Flow<List<RecommendMarketWithPrice>> = flow {
        // marketRepository.getMarketList(MarketType.CRYPTO)
        // TODO: 코인 현재가 불러오는 로직 추가 
        emptyList<RecommendMarketWithPrice>()   
    }
}