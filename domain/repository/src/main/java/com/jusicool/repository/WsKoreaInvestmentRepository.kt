package com.jusicool.repository

import com.jusicool.entity.koreaInvestment.StockPriceEntity
import kotlinx.coroutines.flow.Flow

interface WsKoreaInvestmentRepository {
    fun observeStockTicker(stockCode: String): Flow<StockPriceEntity>
}