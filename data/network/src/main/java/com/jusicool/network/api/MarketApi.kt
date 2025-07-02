package com.jusicool.network.api

import com.jusicool.model.market.MarketListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi {
    @GET("/market/list")
    suspend fun getMarketList(
        @Query("request_param") requestParam: String
    ): List<MarketListResponse>
}