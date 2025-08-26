package com.jusicool.network.api

import com.jusicool.model.market.MarketListResponse
import com.jusicool.model.market.MarketResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi {
    @GET("/market/list")
    fun getMarketList(
        @Query("type") type: String?,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): MarketListResponse

    @GET("/market/search")
    suspend fun searchMarket(
        @Query("query") query: String
    ): List<MarketResponse>
}