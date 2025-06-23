package com.jusicool.repository.order

import com.jusicool.entity.order.OrderModel
import com.jusicool.entity.orderHistory.OrderHistory
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getMonthOrder(): Flow<OrderModel>
    fun getOrderHistory(type: String): Flow<List<OrderHistory>>
}