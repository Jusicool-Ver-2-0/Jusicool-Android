package com.jusicool.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jusicool.entity.market.MarketType
import com.jusicool.model.market.MarketResponse
import com.jusicool.network.datasource.market.MarketDataSource

class MarketListPagingSource(
    private val dataSource: MarketDataSource,
    private val type: MarketType
) : PagingSource<Int, MarketResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarketResponse> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            val response = dataSource.getMarketList(type = type.name, page = page, size = pageSize)

            LoadResult.Page(
                data = response.items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.hasNext) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MarketResponse>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
