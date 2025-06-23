package com.jusicool.network.datasource.order

import com.jusicool.model.order.OrderHistoryResponse
import com.jusicool.model.order.OrderResponse
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {
    fun getMonthOrder(): Flow<OrderResponse>
    fun getOrderHistory(type: String): Flow<List<OrderHistoryResponse>>
}