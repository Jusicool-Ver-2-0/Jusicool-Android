package com.jusicool.entity.market

data class RecommendMarketWithPrice(
    val id: Int,                    // 예: 1
    val market: String,             // 예: "NASDAQ"
    val marketType: MarketType,     // 예: MarketType.CRYPTO
    val koreanName: String,         // 예: "애플"
    val englishName: String,        // 예: "Apple"
    val logoUrl: String?,           // 로고 이미지 URL 또는 로컬 리소스
    val currentPrice: Long,         // 현재 평가 금액 (예: 11111131)
    val profitRate: Double,         // 수익률 (예: 7.9)
) {
    init {
        require(koreanName.isNotBlank()) { "koreanName은 비어 있을 수 없습니다." }
        require(currentPrice >= 0) { "currentPrice는 0 이상이어야 합니다." }
        require(!profitRate.isNaN()) { "profitRate는 숫자여야 합니다." }
    }

    val profit: Int
        get() = ((currentPrice * profitRate) / 100).toInt()

    val isPositive: Boolean
        get() = profit > 0

    val isNegative: Boolean
        get() = profit < 0
}
