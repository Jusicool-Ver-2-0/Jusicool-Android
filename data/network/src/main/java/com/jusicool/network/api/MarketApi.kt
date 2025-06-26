package com.jusicool.network.api

import com.jusicool.model.MarketListResponse
import retrofit2.http.GET

interface MarketApi {
    @GET("/holding/list")
    suspend fun getMarketList(): List<MarketListResponse>
}