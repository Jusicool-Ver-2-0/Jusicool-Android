package com.jusicool.entity.order

data class MonthlyRateWithCurrentPrice(
    val monthlyRate: Double,
    val markets: List<MarketWithCurrentPrice>
) {
    init {
        require(!monthlyRate.isNaN()) { "월간 변동률(monthlyRate)은 숫자여야 합니다." }
        require(monthlyRate.isFinite()) { "월간 변동률(monthlyRate)은 유한수여야 합니다." }
    }

    private val _totalPurchaseAmount: Double by lazy {
        markets.sumOf { it.previousPrice() }
    }

    private val _totalCurrentAmount: Double by lazy {
        markets.sumOf { it.currentPrice }
    }

    /** 전 종목 중 상승 종목의 비율 (0.0 ~ 1.0) */
    fun positiveRatio(): Double =
        markets.count { it.isPositive() }.toDouble() / markets.size * 100

    /** 전 종목 중 하락 종목의 비율 (0.0 ~ 1.0) */
    fun negativeRatio(): Double =
        markets.count { it.isNegative() }.toDouble() / markets.size * 100

    /** 전체 매수원금 합계 */
    fun totalPurchaseAmount() = _totalPurchaseAmount

    /** 전체 현재가 합계 */
    fun totalCurrentAmount() = _totalCurrentAmount

    /** 전체 이익(금액) */
    fun totalProfitAmount() = _totalCurrentAmount - _totalPurchaseAmount
}


data class MarketWithCurrentPrice(
    val market: String,
    val koreanName: String,
    val rate: Double,          // 예: 0.05 = +5%
    val currentPrice: Double   // 현재가
) {
    init {
        require(market.isNotBlank()) { "마켓 정보는 비어 있을 수 없습니다." }
        require(koreanName.isNotBlank()) { "한글 이름은 비어 있을 수 없습니다." }
        require(!rate.isNaN()) { "rate는 숫자여야 합니다." }
        require(rate.isFinite()) { "rate는 유한수여야 합니다." }
        require(!currentPrice.isNaN()) { "currentPrice는 숫자여야 합니다." }
        require(currentPrice.isFinite()) { "currentPrice는 유한수여야 합니다." }
        require(currentPrice >= 0) { "현재가는 음수가 될 수 없습니다." }
    }

    fun isPositive(): Boolean = rate > 0
    fun isNegative(): Boolean = rate < 0

    /**
     * 매수단가(이전 종가)를 역산
     * 예: rate = 0.1(currentPrice 110), then previousPrice = 110 / 1.1 = 100
     */
    fun previousPrice(): Double =
        if (rate != -1.0) currentPrice / (1 + rate)
        else 0.0

    /** 해당 종목 금액 이익 = currentPrice - previousPrice */
    fun profitAmount(): Double =
        currentPrice - previousPrice()
}
