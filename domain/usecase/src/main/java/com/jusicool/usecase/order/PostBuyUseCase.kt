package com.jusicool.usecase.order

import com.jusicool.entity.order.BuyRequestModel
import com.jusicool.repository.order.OrderRepository
import javax.inject.Inject

class PostBuyUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(marketCode:String, quantity: BuyRequestModel) = runCatching {
        orderRepository.postBuy(marketCode = marketCode, quantity = quantity)
    }
}