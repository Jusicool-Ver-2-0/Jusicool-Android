package com.jusicool.entity.price

data class AssetsCurrentPrice(
    val market: String,
    val currentPrice: Double,       // 현재가
    val priceDifference: Double,    // 전일 대비 가격 차이
    val priceDifferenceRate: Double // 전일 대비 등락률 (%)
) {
    init {
        require(currentPrice >= 0) { "현재가는 0 이상이어야 합니다." }
        require(priceDifferenceRate >= -100 && priceDifferenceRate <= 100) { "등락률은 -100% ~ 100% 사이여야 합니다." }
    }

    val isRising: Boolean
        get() = priceDifference > 0

    val isFalling: Boolean
        get() = priceDifference < 0

    val isSteady: Boolean
        get() = priceDifference == 0.0
}
