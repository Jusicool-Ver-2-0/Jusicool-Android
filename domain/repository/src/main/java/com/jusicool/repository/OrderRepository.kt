package com.jusicool.repository

import com.jusicool.entity.order.BuyRequestModel
import com.jusicool.entity.order.BuyReserveModel
import com.jusicool.entity.order.BuyResponseModel
import com.jusicool.entity.order.OrderModel
import com.jusicool.entity.order.SellRequestModel
import com.jusicool.entity.order.SellReserveModel
import com.jusicool.entity.order.SellResponseModel
import com.jusicool.model.order.BuyReserveRequest
import com.jusicool.model.order.SellReserveRequest
import com.jusicool.entity.orderHistory.OrderHistory
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getMonthOrder(): Flow<OrderModel>

    fun postBuy(marketCode: String, quantity: BuyRequestModel): Flow<BuyResponseModel>

    fun postSell(marketCode: String,quantity: SellRequestModel): Flow<SellResponseModel>

    fun postReserveBuy(marketCode: String, body: BuyReserveModel): Flow<Unit>

    fun postReserveSell(marketCode: String, body: SellReserveModel): Flow<Unit>

    fun getOrderHistory(type: String): Flow<List<OrderHistory>>
}