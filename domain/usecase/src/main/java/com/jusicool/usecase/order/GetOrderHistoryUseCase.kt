package com.jusicool.usecase.order

import com.jusicool.entity.orderHistory.OrderHistory
import com.jusicool.entity.orderHistory.OrderHistoryType
import com.jusicool.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderHistoryUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(type: OrderHistoryType): Flow<List<OrderHistory>> =
        orderRepository.getOrderHistory(type = type.name)
}
