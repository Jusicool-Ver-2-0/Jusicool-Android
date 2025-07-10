package com.jusicool.usecase.koreaInvestment

import com.jusicool.entity.koreaInvestment.StockPriceEntity
import com.jusicool.repository.WsKoreaInvestmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRealtimeStockPriceUseCase @Inject constructor(
    private val wsKoreaInvestmentRepository: WsKoreaInvestmentRepository
) {
    operator fun invoke(stockCode: List<String>): Flow<List<StockPriceEntity>> {
        return wsKoreaInvestmentRepository.observeStockTicker(stockCode)
    }
}
