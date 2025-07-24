package com.jusicool.network.datasource.market

import com.jusicool.model.market.MarketListResponse
import com.jusicool.model.market.MarketResponse
import com.jusicool.network.api.MarketApi
import com.jusicool.utils.JusicoolApiHandler
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarketDataSourceImpl @Inject constructor(
    private val marketApi: MarketApi,
) : MarketDataSource {
    override suspend fun getMarketList(
        type: String,
        page: Int,
        size: Int,
    ): MarketListResponse =
        JusicoolApiHandler<MarketListResponse>()
            .httpRequest { marketApi.getMarketList(type = type, page = page, size = size) }
            .sendRequest()

    override fun searchMarket(query: String): Flow<List<MarketResponse>> =
        performApiRequest { marketApi.searchMarket(query = query) }
}
