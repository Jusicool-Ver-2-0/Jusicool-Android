package com.jusicool.repository

import androidx.paging.PagingData
import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun getMarketListPaging(type: MarketType): Flow<PagingData<RecommendMarketWithPrice>>
    fun searchMarket(query: String): Flow<List<Market>>
}