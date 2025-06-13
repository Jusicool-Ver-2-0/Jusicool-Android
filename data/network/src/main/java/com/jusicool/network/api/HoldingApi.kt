package com.jusicool.network.api

import com.jusicool.model.holding.HoldingResponse
import retrofit2.http.GET

interface HoldingApi {
    @GET("/holding/my")
    suspend fun getHolding(): List<HoldingResponse>
}