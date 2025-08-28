package com.jusicool.repository

import android.util.Log
import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.model.koreaInvestment.ws.StockPriceSummary
import com.jusicool.model.mapper.koreaInvestment.toEntity
import com.jusicool.network.datasource.koreaInvestment.ws.KoreaInvestmentWebSocketManagerInterface
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class WsKoreaInvestmentRepositoryImpl @Inject constructor(
    private val webSocketManager: KoreaInvestmentWebSocketManagerInterface
) : WsKoreaInvestmentRepository {

    override fun observeStockTicker(stockCode: List<String>): Flow<List<AssetsCurrentPrice>> =
        callbackFlow {
            webSocketManager.connect(stockCode)

            val job = launch {
                webSocketManager.stockTickerMapFlow
                    .filterNotNull()
                    .map { list -> list.map(StockPriceSummary::toEntity) }
                    .collect { data ->
                        val result = trySend(data)
                        if (result.isFailure) {
                            Log.d("WsRepository", "Flow send 실패: $result")
                        }
                    }
            }

            awaitClose {
                webSocketManager.disconnect()
                job.cancel()
                Log.d("WsRepository", "Flow 종료")
            }
        }

}
