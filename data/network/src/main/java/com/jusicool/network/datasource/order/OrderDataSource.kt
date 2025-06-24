package com.jusicool.network.datasource.order

import com.jusicool.model.order.BuyRequest
import com.jusicool.model.order.BuyReserveRequest
import com.jusicool.model.order.BuyResponse
import com.jusicool.model.order.OrderResponse
import com.jusicool.model.order.SellRequest
import com.jusicool.model.order.SellReserveRequest
import com.jusicool.model.order.SellResponse
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {
    fun getMonthOrder(): Flow<OrderResponse>

    fun postBuy(marketCode: String, quantity: BuyRequest): Flow<BuyResponse>

    fun postSell(marketCode: String,quantity: SellRequest): Flow<SellResponse>

    fun postReserveBuy(marketCode: String, body: BuyReserveRequest): Flow<Unit>

    fun postReserveSell(marketCode: String, body: SellReserveRequest): Flow<Unit>
}
