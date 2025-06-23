package com.jusicool.network.datasource.order

import com.jusicool.model.order.OrderHistoryResponse
import com.jusicool.model.order.OrderResponse
import com.jusicool.network.api.OrderApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderApi: OrderApi
) : OrderDataSource {
    override fun getMonthOrder(): Flow<OrderResponse> =
        performApiRequest { orderApi.getMonthOrder() }

    override fun getOrderHistory(type: String): Flow<List<OrderHistoryResponse>> =
        performApiRequest { orderApi.getOrderHistory(type = type) }
}