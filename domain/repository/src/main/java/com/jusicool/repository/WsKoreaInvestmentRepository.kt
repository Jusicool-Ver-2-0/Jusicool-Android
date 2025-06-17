package com.jusicool.repository

import com.jusicool.model.koreaInvestment.ws.StockPriceSummary
import kotlinx.coroutines.flow.Flow

interface WsKoreaInvestmentRepository {
    fun observeStockTicker(): Flow<StockPriceSummary>
    fun connectToStockTicker(trId: String, stockCode: String)
    fun disconnectFromStockTicker()
}