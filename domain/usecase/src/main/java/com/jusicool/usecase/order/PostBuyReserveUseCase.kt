package com.jusicool.usecase.order

import com.jusicool.entity.order.BuyReserveModel
import com.jusicool.repository.order.OrderRepository
import javax.inject.Inject

class PostBuyReserveUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(marketCode:String, body: BuyReserveModel) = runCatching {
        orderRepository.postReserveBuy(marketCode = marketCode, body = body)
    }
}