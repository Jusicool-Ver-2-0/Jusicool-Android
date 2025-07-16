package com.jusicool.repository

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.model.mapper.market.toEntity
import com.jusicool.network.datasource.market.MarketDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val marketDataSource: MarketDataSource
) : MarketRepository {
    override suspend fun getMarketList(type: MarketType, page: Int, size: Int): List<Market> {
        return marketDataSource
            .getMarketList(type.name, page, size)
            .items.map { it.toEntity() }
    }

    override fun searchMarket(query: String): Flow<List<Market>> {
        return marketDataSource.searchMarket(query).map { marketList ->
            marketList.map { it.toEntity() }
        }
    }
}