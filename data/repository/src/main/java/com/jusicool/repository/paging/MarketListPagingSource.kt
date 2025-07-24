package com.jusicool.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.model.mapper.market.toEntity
import com.jusicool.network.datasource.market.MarketDataSource
import com.jusicool.repository.CryptoRepository
import com.jusicool.repository.KoreaInvestmentRepository
import com.jusicool.usecase.market.util.mergeWithPrices
import javax.inject.Inject

class MarketListPagingSource @Inject constructor(
    private val marketDataSource: MarketDataSource,
    private val stockRepository: KoreaInvestmentRepository,
    private val cryptoRepository: CryptoRepository,
) : PagingSource<Int, RecommendMarketWithPrice>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecommendMarketWithPrice> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val response = marketDataSource.getMarketList(type = "", page = page, size = pageSize)
            val items = response.items
            val marketOrder = items.map { it.market }

            val cryptoList = items.filter { it.marketType == MarketType.CRYPTO.name }
            val stockList = items.filter { it.marketType == MarketType.STOCK.name }

            val mergedRecommendList = mutableListOf<RecommendMarketWithPrice>()

            stockRepository.getStockCurrentPrice(
                marketDivCode = "J",
                markets = stockList.map { it.market },
            ).collect { stockPrices ->
                mergedRecommendList += stockList.map { it.toEntity() }.mergeWithPrices(stockPrices)
            }

            cryptoRepository.getCurrentCryptoPrice(cryptoList.map { it.market })
                .collect { cryptoPrices ->
                    mergedRecommendList += cryptoList.map { it.toEntity() }
                        .mergeWithPrices(cryptoPrices)
                }

            val sorted = mergedRecommendList.sortedBy { marketOrder.indexOf(it.market) }

            LoadResult.Page(
                data = sorted,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.hasNext) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecommendMarketWithPrice>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
