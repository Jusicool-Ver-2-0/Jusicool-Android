package com.jusicool.network.api

import com.jusicool.model.account.AccountResponse
import com.jusicool.model.order.OrderResponse
import retrofit2.http.GET

interface OrderApi {
    @GET("/order/month")
    suspend fun getMonthOrder(): OrderResponse
}