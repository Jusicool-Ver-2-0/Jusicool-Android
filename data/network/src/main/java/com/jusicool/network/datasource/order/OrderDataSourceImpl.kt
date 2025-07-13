package com.jusicool.network.datasource.order

import com.jusicool.model.order.BuyRequest
import com.jusicool.model.order.BuyReserveRequest
import com.jusicool.model.order.BuyResponse
import com.jusicool.model.order.MonthlyRateResponse
import com.jusicool.model.order.OrderHistoryResponse
import com.jusicool.model.order.OrderResponse
import com.jusicool.model.order.SellRequest
import com.jusicool.model.order.SellReserveRequest
import com.jusicool.model.order.SellResponse
import com.jusicool.network.api.OrderApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderApi: OrderApi
) : OrderDataSource {
    override fun getMonthOrder(): Flow<OrderResponse> =
        performApiRequest { orderApi.getMonthOrder() }

    override fun getMonthlyRate(): Flow<MonthlyRateResponse> =
        performApiRequest { orderApi.getMonthlyRate() }

    override fun postBuy(marketCode: String, quantity: BuyRequest): Flow<BuyResponse> =
        performApiRequest { orderApi.postBuy(marketCode = marketCode, quantity = quantity) }

    override fun postSell(marketCode: String, quantity: SellRequest): Flow<SellResponse> =
        performApiRequest { orderApi.postSell(marketCode = marketCode, quantity = quantity) }

    override fun postReserveBuy(marketCode: String, body: BuyReserveRequest): Flow<Unit> =
        performApiRequest { orderApi.postReserveBuy(marketCode = marketCode, body = body) }

    override fun postReserveSell(marketCode: String, body: SellReserveRequest): Flow<Unit> =
        performApiRequest { orderApi.postReserveSell(marketCode = marketCode, body = body) }

    override fun getOrderHistory(type: String): Flow<List<OrderHistoryResponse>> =
        performApiRequest { orderApi.getOrderHistory(type = type) }
}