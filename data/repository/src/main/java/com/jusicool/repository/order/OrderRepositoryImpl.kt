package com.jusicool.repository.order

import com.jusicool.entity.order.OrderModel
import com.jusicool.entity.orderHistory.OrderHistory
import com.jusicool.model.mapper.order.toEntity
import com.jusicool.model.mapper.order.toModel
import com.jusicool.network.datasource.order.OrderDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {
    override fun getMonthOrder(): Flow<OrderModel> {
        return orderDataSource.getMonthOrder().map { it.toModel() }
    }

    override fun getOrderHistory(type: String): Flow<List<OrderHistory>> {
        return orderDataSource.getOrderHistory(type).map { list -> list.map { it.toEntity() } }
    }
}