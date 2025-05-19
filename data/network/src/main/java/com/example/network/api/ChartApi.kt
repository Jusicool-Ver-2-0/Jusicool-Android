package com.example.network.api

import com.jusicool.model.chart.ChartResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ChartApi {
    @GET("v1/ticker")
    suspend fun getChart(
        @Query("markets") markets: String
    ): List<ChartResponse>
}