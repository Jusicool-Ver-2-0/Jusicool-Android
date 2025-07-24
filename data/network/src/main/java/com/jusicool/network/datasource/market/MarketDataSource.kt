package com.jusicool.network.datasource.market

import com.jusicool.model.market.MarketListResponse
import com.jusicool.model.market.MarketResponse
import kotlinx.coroutines.flow.Flow

interface MarketDataSource {
    suspend fun getMarketList(type: String, page: Int, size: Int): MarketListResponse
    fun searchMarket(query: String): Flow<List<MarketResponse>>
}