package com.jusicool.usecase.order

import com.jusicool.entity.order.SellReserveModel
import com.jusicool.repository.OrderRepository
import javax.inject.Inject

class PostSellReserveUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(marketCode:String, body: SellReserveModel) = runCatching {
        orderRepository.postReserveSell(marketCode = marketCode, body = body)
    }
}