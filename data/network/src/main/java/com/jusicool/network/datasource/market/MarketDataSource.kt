package com.jusicool.network.datasource.market

import com.jusicool.model.MarketListResponse
import kotlinx.coroutines.flow.Flow

interface MarketDataSource {
    fun getMarketList(): Flow<List<MarketListResponse>>
}