package com.jusicool.usecase.market

import androidx.paging.PagingData
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendMarketPagingUseCase @Inject constructor(
    private val marketRepository: MarketRepository
) {
    operator fun invoke(type: MarketType): Flow<PagingData<RecommendMarketWithPrice>> {
        return marketRepository.getMarketListPaging(type)
    }
}
