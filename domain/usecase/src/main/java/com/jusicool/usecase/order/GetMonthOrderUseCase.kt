package com.jusicool.usecase.order

import com.jusicool.repository.order.OrderRepository
import javax.inject.Inject

class GetMonthOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke() = runCatching {
        orderRepository.getMonthOrder()
    }
}