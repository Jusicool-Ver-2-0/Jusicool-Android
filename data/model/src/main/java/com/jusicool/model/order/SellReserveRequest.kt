package com.jusicool.model.order

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SellReserveRequest(
    val quantity: Int,
    val price: Int
)