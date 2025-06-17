package com.jusicool.repository

import com.jusicool.entity.koreaInvestment.StockPriceEntity
import com.jusicool.model.mapper.koreaInvestment.toEntity
import com.jusicool.network.datasource.koreaInvestment.ws.KoreaInvestmentWebSocketManagerInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WsKoreaInvestmentRepositoryImpl @Inject constructor(
    private val webSocketManager: KoreaInvestmentWebSocketManagerInterface
) : WsKoreaInvestmentRepository {

    override fun observeStockTicker(): Flow<StockPriceEntity> {
        return webSocketManager.stockTickerFlow.map { it.toEntity() }
    }

    override fun connectToStockTicker(trId: String, stockCode: String) {
        webSocketManager.connect(trId, stockCode)
    }

    override fun disconnectFromStockTicker() {
        webSocketManager.disconnect()
    }
}
