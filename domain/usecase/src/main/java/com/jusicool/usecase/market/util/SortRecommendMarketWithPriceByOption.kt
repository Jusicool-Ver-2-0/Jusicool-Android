package com.jusicool.usecase.market.util

import com.jusicool.entity.market.RecommendMarketWithPrice

enum class SortOption {
    BY_PROFIT_DESC,
    BY_PRICE_DESC,
    BY_NAME_ASC,
}

internal fun List<RecommendMarketWithPrice>.sortedByOption(
    option: SortOption,
): List<RecommendMarketWithPrice> = when (option) {
    SortOption.BY_PROFIT_DESC -> sortedByDescending { it.profitRate }
    SortOption.BY_PRICE_DESC -> sortedByDescending { it.currentPrice }
    SortOption.BY_NAME_ASC -> sortedBy { it.koreanName }
}