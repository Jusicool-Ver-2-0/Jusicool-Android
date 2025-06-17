package com.jusicool.usecase.koreaInvestment

import com.jusicool.repository.WsKoreaInvestmentRepository
import javax.inject.Inject

class UnsubscribeRealtimeStockPriceUseCase @Inject constructor(
    private val wsKoreaInvestmentRepository: WsKoreaInvestmentRepository
) {
    operator fun invoke() {
        wsKoreaInvestmentRepository.disconnectFromStockTicker()
    }
}
