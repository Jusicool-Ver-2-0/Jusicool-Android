package com.jusicool.model.order

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderResponse(
    val rate: Int,
    @Json(name = "order_count") val orderCount: Int
)