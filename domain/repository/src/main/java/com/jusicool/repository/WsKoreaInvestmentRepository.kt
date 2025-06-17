package com.jusicool.repository

import com.jusicool.entity.koreaInvestment.StockPriceEntity
import kotlinx.coroutines.flow.Flow

interface WsKoreaInvestmentRepository {
    fun observeStockTicker(): Flow<StockPriceEntity>
    fun connectToStockTicker(stockCode: String)
    fun disconnectFromStockTicker()
}