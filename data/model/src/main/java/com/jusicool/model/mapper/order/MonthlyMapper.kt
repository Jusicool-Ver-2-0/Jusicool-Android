package com.jusicool.model.mapper.order

import com.jusicool.entity.order.DailyRate
import com.jusicool.entity.order.MarketRate
import com.jusicool.entity.order.MonthlyRate
import com.jusicool.model.order.MonthlyRateResponse
import com.jusicool.model.order.RateByMarket
import java.time.LocalDate

fun MonthlyRateResponse.toEntity(): MonthlyRate {

    val dailyRatesMap: Map<String, List<RateByMarket>> = markets.groupBy { it.date }

    val dailyRates = dailyRatesMap.map { (dateStr, marketList) ->
        DailyRate(
            date = LocalDate.parse(dateStr),
            marketRates = marketList.map { it.toDomain() }
        )
    }

    return MonthlyRate(
        monthlyRate = this.monthlyRate,
        dailyRates = dailyRates
    )
}

fun RateByMarket.toDomain(): MarketRate = MarketRate(
    market = this.market,
    koreanName = this.koreanName,
    rate = this.rate,
    proceed = this.proceed
)
