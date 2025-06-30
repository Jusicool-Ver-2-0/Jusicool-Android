package com.jusicool.entity.order

data class MonthlyRate(
    val monthlyRate: Double,
    val marketRates: List<MarketRate>
)

data class MarketRate(
    val market: String,
    val koreanName: String,
    val rate: Double,
) {
    init {
        require(market.isNotBlank()) { "마켓 정보는 비어 있을 수 없습니다." }
        require(koreanName.isNotBlank()) { "한글 이름은 비어 있을 수 없습니다." }
    }

    fun isPositive(): Boolean = rate > 0

    fun isNegative(): Boolean = rate < 0
}
