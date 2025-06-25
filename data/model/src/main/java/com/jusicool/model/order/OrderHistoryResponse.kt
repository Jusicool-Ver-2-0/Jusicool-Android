package com.jusicool.model.order

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderHistoryResponse(
    val id: Int,
    val market: String,
    @Json(name = "order_type") val orderType: String,
    val price: Int,
    val quantity: Int,
    @Json(name = "reserve_type") val reserveType: String,
    val status: String
)