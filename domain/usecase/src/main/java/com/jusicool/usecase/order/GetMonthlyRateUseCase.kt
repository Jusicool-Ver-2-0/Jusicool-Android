package com.jusicool.usecase.order

import com.jusicool.entity.order.MonthlyRate
import com.jusicool.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMonthlyRateUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(): Flow<MonthlyRate> =
        orderRepository.getMonthlyRate()
}