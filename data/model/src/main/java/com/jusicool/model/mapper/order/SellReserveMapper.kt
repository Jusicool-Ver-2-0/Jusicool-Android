package com.jusicool.model.mapper.order

import com.jusicool.entity.order.SellReserveModel
import com.jusicool.model.order.SellReserveRequest

fun SellReserveModel.toDto(): SellReserveRequest =
    SellReserveRequest(
        quantity = this.quantity,
        price = this.price
    )