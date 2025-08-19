package com.jusicool.entity.price

import java.time.LocalDateTime

/**
 * 주식 차트 캔들 데이터 (1분/일봉/주봉 등 공통 구조)
 */
data class MinuteCandleEntity(
    val dateTime: LocalDateTime,   // 날짜 및 시간 (예: "20250614", "20250614-0930")
    val openPrice: Double,         // 시가
    val closePrice: Double,        // 종가
    val highPrice: Double,         // 고가
    val lowPrice: Double,          // 저가
    val volume: Double,            // 거래량
) {
    init {
        require(openPrice > 0) { "시가(openPrice)는 0보다 커야 합니다." }
        require(closePrice > 0) { "종가(closePrice)는 0보다 커야 합니다." }
        require(volume >= 0) { "거래량(volumn)는 0보다 커야 합니다." }
        require(highPrice >= maxOf(openPrice, closePrice, lowPrice)) {
            "고가(highPrice)는 open/close/low 중 가장 높아야 합니다."
        }
        require(lowPrice <= minOf(openPrice, closePrice, highPrice)) {
            "저가(lowPrice)는 open/close/high 중 가장 낮아야 합니다."
        }
    }

    /** 양봉 여부 */
    val isBullish: Boolean
        get() = closePrice > openPrice

    /** 음봉 여부 */
    val isBearish: Boolean
        get() = closePrice < openPrice

    /** 종가 - 시가 */
    val priceChange: Double
        get() = closePrice - openPrice

    /** 등락률 (%, 소수점 기준) */
    val priceChangeRatio: Double
        get() = if (openPrice == 0.0) 0.0 else (priceChange / openPrice) * 100
}