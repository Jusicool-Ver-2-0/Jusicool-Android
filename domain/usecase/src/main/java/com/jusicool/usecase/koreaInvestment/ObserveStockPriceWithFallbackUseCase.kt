package com.jusicool.usecase.koreaInvestment

import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.repository.WsKoreaInvestmentRepository
import com.jusicool.utils.isStockMarketOpen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ObserveStockPriceWithFallbackUseCase @Inject constructor(
    private val wsKoreaInvestmentRepository: WsKoreaInvestmentRepository,
    private val getCurrentStockPriceUseCase: GetCurrentStockPriceUseCase,
) {

    operator fun invoke(
        stockCodes: List<String>,
        delayMs: Long = 2_000L,
    ): Flow<List<AssetsCurrentPrice>> {
        return if (isStockMarketOpen()) {
            // 장이 열렸을 때: 웹소켓 + fallback
            val realtimeFlow: Flow<List<AssetsCurrentPrice>> =
                wsKoreaInvestmentRepository.observeStockTicker(stockCodes)

            val fallbackFlow: Flow<List<AssetsCurrentPrice>> =
                flow {
                    delay(delayMs)
                    emitAll(getCurrentStockPriceUseCase(stockCodes))
                }

            merge(realtimeFlow, fallbackFlow)
                .scan(emptyMap<String, AssetsCurrentPrice>()) { acc, prices ->
                    acc.toMutableMap().apply {
                        prices.forEach { put(it.market, it) }
                    }
                }
                .map { latestMap ->
                    stockCodes.mapNotNull { latestMap[it] }
                }
                .distinctUntilChanged()

        } else {
            // 장이 닫혔을 때: API만 호출
            getCurrentStockPriceUseCase(stockCodes)
                .map { prices ->
                    val map = prices.associateBy { it.market }
                    stockCodes.mapNotNull { map[it] }
                }
                .distinctUntilChanged()
        }
    }
}
