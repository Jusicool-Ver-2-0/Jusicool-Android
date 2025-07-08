package com.jusicool.model.mapper.order

import com.jusicool.entity.order.MarketRate
import com.jusicool.entity.order.MonthlyRate
import com.jusicool.model.order.MonthlyRateResponse
import com.jusicool.model.order.RateByMarket
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun MonthlyRateResponse.toEntity(): MonthlyRate =
    MonthlyRate(
        monthlyRate = this.monthlyRate,
        marketRates = this.markets.map { it.toEntity() }
    )

fun RateByMarket.toEntity(): MarketRate =
    MarketRate(
        market = this.market,
        koreanName = this.koreanName,
        rate = this.rate,
        proceed = this.proceed,
        date = LocalDate.parse(this.date, dateFormatter)
    )
