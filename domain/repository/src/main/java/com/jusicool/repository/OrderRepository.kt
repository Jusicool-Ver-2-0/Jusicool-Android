package com.jusicool.repository

import com.jusicool.entity.order.OrderModel
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getMonthOrder(): Flow<OrderModel>
}