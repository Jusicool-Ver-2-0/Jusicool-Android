package com.jusicool.repository

import com.jusicool.entity.market.Market
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun getMarketList():  Flow<List<Market>>
}