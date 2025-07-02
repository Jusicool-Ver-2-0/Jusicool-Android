package com.jusicool.model.mapper.koreaInvestment

import com.jusicool.entity.koreaInvestment.StockPriceEntity
import com.jusicool.model.koreaInvestment.ws.StockPriceSummary

fun StockPriceSummary.toEntity(): StockPriceEntity {
    val signMapping = mapOf(
        1 to "1", // 상한 → 상승
        2 to "1", // 상승 → 상승
        3 to "3", // 보합 → 보합
        4 to "2", // 하한 → 하락
        5 to "2"  // 하락 → 하락
    )
    val priceChangeSign = signMapping[this.prevDiffSign] ?: "3"
    val timeParsed = StockPriceEntity.parseTime(this.time)

    return StockPriceEntity(
        stockCode = this.stockCode,
        currentPrice = this.currentPrice,
        priceChange = this.prevDiffPrice,
        priceChangeRate = this.prevDiffRatio,
        priceChangeSign = priceChangeSign,
        time = timeParsed
    )
}
