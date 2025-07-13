package com.jusicool.network.datasource.market

import com.jusicool.model.market.MarketListResponse
import com.jusicool.network.api.MarketApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarketDataSourceImpl @Inject constructor(
    private val marketApi: MarketApi,
) : MarketDataSource {
    override fun getMarketList(requestParam: String): Flow<List<MarketListResponse>> =
        performApiRequest { marketApi.getMarketList(requestParam = requestParam) }
}
