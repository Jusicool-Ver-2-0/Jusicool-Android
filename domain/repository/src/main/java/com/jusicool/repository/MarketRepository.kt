package com.jusicool.repository

import com.jusicool.entity.holding.HoldingModel
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun getMarketList(): Flow<List<HoldingModel>>
}