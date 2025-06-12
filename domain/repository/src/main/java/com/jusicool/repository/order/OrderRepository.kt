package com.jusicool.repository.order

import com.jusicool.entity.order.OrderModel
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getMonthOrder(): Flow<OrderModel>
}