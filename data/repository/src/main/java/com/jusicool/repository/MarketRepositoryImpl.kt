package com.jusicool.repository

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.network.datasource.market.MarketDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val marketDataSource: MarketDataSource
) : MarketRepository {
    override fun getMarketList(): Flow<List<HoldingModel>> {
        return marketDataSource.getMarketList()
    }
}