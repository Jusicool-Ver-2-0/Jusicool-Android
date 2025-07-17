package com.jusicool.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.model.mapper.market.toEntity
import com.jusicool.network.datasource.market.MarketDataSource
import com.jusicool.repository.paging.MarketListPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val marketDataSource: MarketDataSource,
    private val stockRepository: KoreaInvestmentRepository,
    private val cryptoRepository: CryptoRepository,
) : MarketRepository {

    override fun getMarketListPaging(
        type: MarketType
    ): Flow<PagingData<RecommendMarketWithPrice>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MarketListPagingSource(
                    marketDataSource = marketDataSource,
                    stockRepository = stockRepository,
                    cryptoRepository = cryptoRepository
                )
            }
        ).flow
    }

    override fun searchMarket(query: String): Flow<List<Market>> {
        return marketDataSource.searchMarket(query).map { list ->
            list.map { it.toEntity() }
        }
    }
}
