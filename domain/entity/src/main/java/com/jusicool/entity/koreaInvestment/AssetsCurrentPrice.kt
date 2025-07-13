package com.jusicool.entity.koreaInvestment

data class AssetsCurrentPrice(
    val market: String,
    val currentPrice: Int,       // 현재가
    val priceDifference: Int,    // 전일 대비 가격 차이
    val priceDifferenceRate: Double // 전일 대비 등락률 (%)
) {
    init {
        require(currentPrice >= 0) { "현재가는 0 이상이어야 합니다." }
        require(priceDifferenceRate >= -100 && priceDifferenceRate <= 100) { "등락률은 -100% ~ 100% 사이여야 합니다." }
    }

    // 상승장 여부
    fun isRising(): Boolean = priceDifference > 0

    // 하락장 여부
    fun isFalling(): Boolean = priceDifference < 0

    // 보합 여부
    fun isSteady(): Boolean = priceDifference == 0
}
