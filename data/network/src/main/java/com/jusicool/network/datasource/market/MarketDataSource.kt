package com.jusicool.network.datasource.market

import com.jusicool.model.market.MarketListResponse
import kotlinx.coroutines.flow.Flow

interface MarketDataSource {
    fun getMarketList(requestParam: String): Flow<List<MarketListResponse>>
    fun searchMarket(query: String): Flow<List<MarketListResponse>>
}