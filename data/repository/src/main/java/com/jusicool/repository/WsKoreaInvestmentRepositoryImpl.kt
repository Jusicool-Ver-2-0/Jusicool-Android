package com.jusicool.repository

import com.jusicool.entity.koreaInvestment.StockPriceEntity
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

    override fun observeStockTicker(stockCode: String): Flow<StockPriceEntity> = callbackFlow {
        webSocketManager.connect(stockCode)

        val job = launch {
            webSocketManager.stockTickerFlow
                .filterNotNull()
                .map { it.toEntity() }
                .collect { trySend(it).isSuccess }
        }

        awaitClose {
            webSocketManager.disconnect()
            job.cancel()
        }
    }

}
