package com.jusicool.repository

import com.jusicool.entity.market.Market
import com.jusicool.model.mapper.market.toEntity
import com.jusicool.network.datasource.market.MarketDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val marketDataSource: MarketDataSource
) : MarketRepository {
    override fun getMarketList(): Flow<List<Market>> {
        return marketDataSource.getMarketList().map { marketList ->
            marketList.map { it.toEntity() }
        }
    }
}