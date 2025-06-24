package com.jusicool.usecase.order

import com.jusicool.entity.order.SellRequestModel
import com.jusicool.repository.order.OrderRepository
import javax.inject.Inject

class PostSellUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(marketCode:String, quantity: SellRequestModel) = runCatching {
        orderRepository.postSell(marketCode = marketCode, quantity = quantity)
    }
}