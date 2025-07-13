package com.jusicool.usecase.market

import com.jusicool.entity.market.RecommendMarketWithPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTotalRecommendMarketListUseCase @Inject constructor(
    private val getStockRecommendMarketListUseCase: GetStockRecommendMarketListUseCase,
    private val getCryptoRecommendMarketListUseCase: GetCryptoRecommendMarketListUseCase,
) {
    operator fun invoke(
        sortBy: SortOption = SortOption.BY_PRICE_DESC,
    ): Flow<List<RecommendMarketWithPrice>> {
        return getCryptoRecommendMarketListUseCase()
        combine(
            getStockRecommendMarketListUseCase(),
            getCryptoRecommendMarketListUseCase()
        ) { stocks, cryptos ->
            (stocks + cryptos).sortedByOption(sortBy)
        }
    }
}

enum class SortOption {
    BY_PROFIT_DESC,
    BY_PRICE_DESC,
    BY_NAME_ASC,
}

private fun List<RecommendMarketWithPrice>.sortedByOption(
    option: SortOption
): List<RecommendMarketWithPrice> = when (option) {
    SortOption.BY_PROFIT_DESC -> sortedByDescending { it.profitRate }
    SortOption.BY_PRICE_DESC -> sortedByDescending { it.currentPrice }
    SortOption.BY_NAME_ASC -> sortedBy { it.koreanName }
}