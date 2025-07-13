package com.jusicool.model.mapper.koreaInvestment

import com.jusicool.entity.koreaInvestment.AssetsCurrentPrice
import com.jusicool.model.koreaInvestment.ws.StockPriceSummary

fun StockPriceSummary.toEntity(): AssetsCurrentPrice {
    return AssetsCurrentPrice(
        market = this.stockCode,
        currentPrice = this.currentPrice.toInt(),
        priceDifference = this.prevDiffPrice.toIntWithSign(prevDiffSign),
        priceDifferenceRate = this.prevDiffRatio
    )
}

/**
 * prevDiffSign 부호값에 따라 전일 대비 가격에 부호 적용
 * 1: 상한, 2: 상승 → +
 * 3: 보합 → 0
 * 4: 하한, 5: 하락 → -
 */
private fun Double.toIntWithSign(sign: Int): Int {
    val absValue = this.toInt()
    return when (sign) {
        1, 2 -> absValue
        4, 5 -> -absValue
        3 -> 0
        else -> 0
    }
}
