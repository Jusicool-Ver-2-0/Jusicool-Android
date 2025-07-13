package com.jusicool.usecase.order

import com.jusicool.entity.order.DailyRate
import com.jusicool.entity.order.MarketRate
import com.jusicool.entity.order.MonthlyRate
import com.jusicool.repository.OrderRepository
import com.jusicool.utils.isValidCryptoMarketCode
import com.jusicool.utils.isValidStockMarketCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMonthlyRateUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(): Flow<MonthlyRateGroup> =
        orderRepository.getMonthlyRate().map { allMonthlyRate ->
            val crypto = allMonthlyRate.filterBy { it.market.isValidCryptoMarketCode() }
            val stock = allMonthlyRate.filterBy { it.market.isValidStockMarketCode() }

            MonthlyRateGroup(
                all = allMonthlyRate,
                crypto = crypto,
                stock = stock
            )
        }

    private fun MonthlyRate.filterBy(predicate: (MarketRate) -> Boolean): MonthlyRate {
        val filteredDailyRates = dailyRates.mapNotNull { dailyRate ->
            val filteredMarkets = dailyRate.marketRates.filter(predicate)
            if (filteredMarkets.isNotEmpty()) {
                DailyRate(dailyRate.date, filteredMarkets)
            } else null
        }

        return MonthlyRate(
            monthlyRate = this.monthlyRate,
            dailyRates = filteredDailyRates
        )
    }
}

data class MonthlyRateGroup(
    val all: MonthlyRate,
    val crypto: MonthlyRate,
    val stock: MonthlyRate
)