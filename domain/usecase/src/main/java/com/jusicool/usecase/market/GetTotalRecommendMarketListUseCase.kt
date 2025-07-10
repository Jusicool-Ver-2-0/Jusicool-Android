package com.jusicool.usecase.market

import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.usecase.market.util.SortOption
import com.jusicool.usecase.market.util.sortedByOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTotalRecommendMarketListUseCase @Inject constructor(
    private val getStockRecommendMarketListUseCase: GetStockRecommendMarketListUseCase,
    private val getCryptoRecommendMarketListUseCase: GetCryptoRecommendMarketListUseCase,
) {
    operator fun invoke(
        sortBy: SortOption = SortOption.BY_PRICE_DESC
    ): Flow<List<RecommendMarketWithPrice>> {
        return combine(
            getStockRecommendMarketListUseCase(),
            getCryptoRecommendMarketListUseCase()
        ) { stocks, cryptos ->
            (stocks + cryptos).sortedByOption(sortBy)
        }
    }
}
