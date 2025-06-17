package com.jusicool.repository

import com.jusicool.model.koreaInvestment.ws.StockPriceSummary
import com.jusicool.network.datasource.koreaInvestment.ws.KoreaInvestmentWebSocketManagerInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WsKoreaInvestmentRepositoryImpl @Inject constructor(
    private val webSocketManager: KoreaInvestmentWebSocketManagerInterface
) : WsKoreaInvestmentRepository {

    override fun observeStockTicker(): Flow<StockPriceSummary> {
        return webSocketManager.stockTickerFlow
    }

    override fun connectToStockTicker(trId: String, stockCode: String) {
        webSocketManager.connect(trId, stockCode)
    }

    override fun disconnectFromStockTicker() {
        webSocketManager.disconnect()
    }
}
