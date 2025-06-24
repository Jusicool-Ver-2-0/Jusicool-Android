package com.jusicool.model.order

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BuyRequest(
    val quantity: Int
)