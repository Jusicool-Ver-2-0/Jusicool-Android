package com.jusicool.model.mapper.order

import com.jusicool.entity.order.OrderModel
import com.jusicool.model.order.OrderResponse

fun OrderResponse.toModel(): OrderModel =
    OrderModel(
        rate = this.rate,
        orderCount = this.orderCount
    )