package com.jusicool.usecase.koreaInvestment

import com.jusicool.entity.koreaInvestment.AssetsCurrentPrice
import com.jusicool.repository.WsKoreaInvestmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRealtimeStockPriceUseCase @Inject constructor(
    private val wsKoreaInvestmentRepository: WsKoreaInvestmentRepository
) {
    operator fun invoke(stockCode: List<String>): Flow<List<AssetsCurrentPrice>> =
        wsKoreaInvestmentRepository.observeStockTicker(stockCode)
}
