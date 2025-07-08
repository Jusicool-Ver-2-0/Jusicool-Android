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
        status = OrderStatus.valueOf(this.status),
        quantity = this.quantity,
        executePrice = this.executePrice,
        reservePrice = this.reservePrice
    )