package com.jusicool.usecase.koreaInvestment

import com.jusicool.repository.WsKoreaInvestmentRepository
import javax.inject.Inject

class SubscribeRealtimeStockPriceUseCase @Inject constructor(
    private val wsKoreaInvestmentRepository: WsKoreaInvestmentRepository,
) {
    operator fun invoke(stockCode: String) {
        wsKoreaInvestmentRepository.connectToStockTicker(stockCode)
    }
}
