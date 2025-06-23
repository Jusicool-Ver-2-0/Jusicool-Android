package com.jusicool.network.api

import com.jusicool.model.order.OrderHistoryResponse
import com.jusicool.model.order.OrderResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderApi {
    @GET("/order/month")
    suspend fun getMonthOrder(): OrderResponse

    @GET("/order/my")
    suspend fun getOrderHistory(
        @Query("type") type: String,
    ): List<OrderHistoryResponse>
}