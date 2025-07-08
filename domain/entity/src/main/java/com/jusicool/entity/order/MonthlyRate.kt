package com.jusicool.entity.order

import java.time.LocalDate

data class MonthlyRate(
    val monthlyRate: Double,
    val marketRates: List<MarketRate>
) {
    fun monthlyProfit(): Int = marketRates.sumOf { it.proceed }
}

data class MarketRate(
    val market: String,
    val koreanName: String,
    val rate: Double,
    val proceed: Int,
    val date: LocalDate,
) {
    init {
        require(market.isNotBlank()) { "market는 비어 있을 수 없습니다." }
        require(koreanName.isNotBlank()) { "koreanName은 비어 있을 수 없습니다." }
    }

    fun isPositive(): Boolean = rate > 0

    fun isNegative(): Boolean = rate < 0
}