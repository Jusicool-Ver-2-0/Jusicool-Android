package com.jusicool.entity.order

import java.time.LocalDate

data class MonthlyRate(
    val monthlyRate: Double,
    val dailyRates: List<DailyRate>
) {
    val monthlyProfit: Int
        get() = dailyRates.sumOf { it.dailyProfit }
}

data class DailyRate(
    val date: LocalDate,
    val marketRates: List<MarketRate>
) {
    val dailyProfit: Int
        get() = marketRates.sumOf { it.proceed }
}

data class MarketRate(
    val market: String,
    val koreanName: String,
    val rate: Double,
    val proceed: Int,
) {
    init {
        require(market.isNotBlank()) { "market는 비어 있을 수 없습니다." }
        require(koreanName.isNotBlank()) { "koreanName은 비어 있을 수 없습니다." }
    }

    val uniqueKey: String
        get() = "${market}_${rate}_${proceed}"

    val isPositive: Boolean
        get() = rate > 0


    val isNegative: Boolean
        get() = rate < 0
}