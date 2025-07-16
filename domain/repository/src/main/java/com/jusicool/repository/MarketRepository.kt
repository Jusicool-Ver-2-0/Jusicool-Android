package com.jusicool.repository

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    suspend fun getMarketList(type: MarketType, page: Int, size: Int): List<Market>
    fun searchMarket(query: String): Flow<List<Market>>
}