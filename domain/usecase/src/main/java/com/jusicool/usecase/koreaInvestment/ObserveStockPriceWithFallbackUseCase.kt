package com.jusicool.usecase.koreaInvestment

import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.repository.WsKoreaInvestmentRepository
import com.jusicool.utils.isStockMarketOpen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ObserveStockPriceWithFallbackUseCase @Inject constructor(
    private val wsKoreaInvestmentRepository: WsKoreaInvestmentRepository,
    private val getCurrentStockPriceUseCase: GetCurrentStockPriceUseCase,
) {
    operator fun invoke(
        stockCodes: List<String>,
        delayMs: Long = 2_000L,
    ): Flow<List<AssetsCurrentPrice>> {
        if (stockCodes.isEmpty()) return flowOf(emptyList())

        if (isStockMarketOpen()) {
            return channelFlow {
                val latestMap = mutableMapOf<String, AssetsCurrentPrice>()

                // 웹소켓 실시간 구독
                launch {
                    wsKoreaInvestmentRepository.observeStockTicker(stockCodes).collect { prices ->
                        var updated = false
                        prices.forEach { price ->
                            if (latestMap[price.market] != price) {
                                latestMap[price.market] = price
                                updated = true
                            }
                        }
                        if (updated) send(stockCodes.mapNotNull { latestMap[it] })
                    }
                }

                // fallback: delay 후 아직 도달하지 않은 코드만 API로 요청
                launch {
                    delay(delayMs)
                    val marketsNotReceived = stockCodes.filter { it !in latestMap.keys }
                    if (marketsNotReceived.isNotEmpty()) {
                        getCurrentStockPriceUseCase(marketsNotReceived).collect { prices ->
                            var updated = false
                            prices.forEach { price ->
                                if (latestMap[price.market] != price) {
                                    latestMap[price.market] = price
                                    updated = true
                                }
                            }
                            if (updated) send(stockCodes.mapNotNull { latestMap[it] })
                        }
                    }
                }
            }.distinctUntilChanged()
        } else {
            // 장 종료: 무조건 API로만
            return getCurrentStockPriceUseCase(stockCodes)
                .map { prices ->
                    val map = prices.associateBy { it.market }
                    stockCodes.mapNotNull { map[it] }
                }
                .distinctUntilChanged()
        }
    }
}
