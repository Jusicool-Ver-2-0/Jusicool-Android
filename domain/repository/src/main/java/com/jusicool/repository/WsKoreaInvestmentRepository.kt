package com.jusicool.repository

import com.jusicool.entity.price.AssetsCurrentPrice
import kotlinx.coroutines.flow.Flow

interface WsKoreaInvestmentRepository {
    fun observeStockTicker(stockCode: List<String>): Flow<List<AssetsCurrentPrice>>
}