package com.jusicool.entity.order

import java.time.LocalDate

data class MonthlyRate(
    val monthlyRate: Double,
    val dailyRates: List<DailyRate>
) {
    fun monthlyProfit(): Int = dailyRates.sumOf { it.dailyProfit() }
}

data class DailyRate(
    val date: LocalDate,
    val marketRates: List<MarketRate>
) {
    fun dailyProfit(): Int = marketRates.sumOf { it.proceed }
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

    fun isPositive(): Boolean = rate > 0

    fun isNegative(): Boolean = rate < 0
}