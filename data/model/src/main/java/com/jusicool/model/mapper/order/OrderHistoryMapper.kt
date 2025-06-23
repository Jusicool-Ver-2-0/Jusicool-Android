package com.jusicool.model.mapper.order

import com.jusicool.entity.orderHistory.OrderHistory
import com.jusicool.entity.orderHistory.OrderStatus
import com.jusicool.entity.orderHistory.OrderType
import com.jusicool.entity.orderHistory.ReserveType
import com.jusicool.model.order.OrderHistoryResponse

fun OrderHistoryResponse.toEntity(): OrderHistory =
    OrderHistory(
        id = this.id,
        market = this.market,
        orderType = OrderType.valueOf(this.orderType),
        reserveType = ReserveType.valueOf(this.reserveType),
        quantity = this.quantity,
        price = this.price,
        status = OrderStatus.valueOf(this.status)
    )