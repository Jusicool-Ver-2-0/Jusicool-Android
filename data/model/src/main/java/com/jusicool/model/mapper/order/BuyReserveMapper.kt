package com.jusicool.model.mapper.order

import com.jusicool.entity.order.BuyReserveModel
import com.jusicool.model.order.BuyReserveRequest

fun BuyReserveModel.toDto():  BuyReserveRequest=
    BuyReserveRequest(
        quantity = this.quantity,
        price = this.price
    )