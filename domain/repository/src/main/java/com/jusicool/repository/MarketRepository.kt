package com.jusicool.repository

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun getMarketList(type: MarketType, page: Int, size: Int): Flow<List<Market>>
    fun searchMarket(query: String): Flow<List<Market>>
}