package com.jusicool.model.mapper.order

import com.jusicool.entity.order.MarketRate
import com.jusicool.entity.order.MonthlyRate
import com.jusicool.model.order.MonthlyRateResponse
import com.jusicool.model.order.RateByMarket

fun MonthlyRateResponse.toEntity(): MonthlyRate =
    MonthlyRate(
        monthlyRate = this.monthlyRate,
        marketRates = this.markets.map { it.toEntity() }
    )

fun RateByMarket.toEntity(): MarketRate =
    MarketRate(
        market = this.market,
        rate = this.rate,
        koreanName = this.koreanName,
    )
