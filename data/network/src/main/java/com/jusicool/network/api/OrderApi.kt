package com.jusicool.network.api

import com.jusicool.model.order.BuyRequest
import com.jusicool.model.order.BuyReserveRequest
import com.jusicool.model.order.BuyResponse
import com.jusicool.model.order.OrderHistoryResponse
import com.jusicool.model.order.OrderResponse
import com.jusicool.model.order.SellRequest
import com.jusicool.model.order.SellReserveRequest
import com.jusicool.model.order.SellResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderApi {
    @GET("/order/my")
    suspend fun getOrderHistory(
        @Query("type") type: String,
    ): List<OrderHistoryResponse>

    @GET("/order/month")
    suspend fun getMonthOrder(): OrderResponse

    @GET("/order/my")
    suspend fun getMonthlyRate(): List<OrderHistoryResponse>

  @POST("/order/buy/{market_code}")
    suspend fun postBuy(
        @Path("market_code") marketCode: String,
        @Body quantity: BuyRequest
    ): BuyResponse

    @POST("/order/sell/{market_code}")
    suspend fun postSell(
        @Path("market_code") marketCode: String,
        @Body quantity: SellRequest
    ): SellResponse

    @POST("/order/buy/reserve/{market_code}")
    suspend fun postReserveBuy(
        @Path("market_code") marketCode: String,
        @Body body: BuyReserveRequest
    ): Response<Unit>

    @POST("/order/sell/reserve/{market_code}")
    suspend fun postReserveSell(
        @Path("market_code") marketCode: String,
        @Body body: SellReserveRequest
    ): Response<Unit>
}