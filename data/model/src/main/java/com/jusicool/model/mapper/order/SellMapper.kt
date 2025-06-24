package com.jusicool.model.mapper.order

import com.jusicool.entity.order.SellRequestModel
import com.jusicool.entity.order.SellResponseModel
import com.jusicool.model.order.SellRequest
import com.jusicool.model.order.SellResponse

fun SellRequestModel.toDto(): SellRequest =
    SellRequest(
        quantity = this.quantity
    )

fun SellResponse.toModel(): SellResponseModel =
    SellResponseModel(
        price = this.price
    )