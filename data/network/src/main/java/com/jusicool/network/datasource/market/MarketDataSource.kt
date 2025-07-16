package com.jusicool.network.datasource.market

import com.jusicool.model.market.MarketResponse
import kotlinx.coroutines.flow.Flow

interface MarketDataSource {
    fun getMarketList(requestParam: String): Flow<List<MarketResponse>>
    fun searchMarket(query: String): Flow<List<MarketResponse>>
}