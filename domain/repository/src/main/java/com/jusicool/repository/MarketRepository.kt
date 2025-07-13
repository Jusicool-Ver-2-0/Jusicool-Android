package com.jusicool.repository

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun getMarketList(requestParam: MarketType):  Flow<List<Market>>
}