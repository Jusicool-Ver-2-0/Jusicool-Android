package com.jusicool.model.mapper.order

import com.jusicool.entity.order.BuyRequestModel
import com.jusicool.entity.order.BuyResponseModel
import com.jusicool.model.order.BuyRequest
import com.jusicool.model.order.BuyResponse

fun BuyRequestModel.toDto(): BuyRequest =
    BuyRequest(
        quantity = this.quantity
    )

fun BuyResponse.toModel(): BuyResponseModel =
    BuyResponseModel(
        price = this.price
    )