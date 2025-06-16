package com.jusicool.entity.koreaInvestment

/**
 * 주식 차트 캔들 데이터 (1분/일봉/주봉 등 공통 구조)
 */
data class StockCandleEntity(
    val dateTime: String,     // 날짜 및 시간 (예: "20250614", "20250614-0930")
    val openPrice: Long,      // 시가
    val closePrice: Long,     // 종가
    val highPrice: Long,      // 고가
    val lowPrice: Long        // 저가
) {
    init {
        require(openPrice > 0) { "시가(openPrice)는 0보다 커야 합니다." }
        require(closePrice > 0) { "종가(closePrice)는 0보다 커야 합니다." }
        require(highPrice >= maxOf(openPrice, closePrice, lowPrice)) {
            "고가(highPrice)는 open/close/low 중 가장 높아야 합니다."
        }
        require(lowPrice <= minOf(openPrice, closePrice, highPrice)) {
            "저가(lowPrice)는 open/close/high 중 가장 낮아야 합니다."
        }
    }

    /**
     * 상승/하락 여부 판단 (도메인 로직 추가)
     */
    val isRising: Boolean
        get() = closePrice > openPrice

    val changeRate: Double
        get() = ((closePrice - openPrice).toDouble() / openPrice * 100)
}
