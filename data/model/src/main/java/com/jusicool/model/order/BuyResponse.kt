package com.jusicool.model.order

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BuyResponse(
    val price: Int
)