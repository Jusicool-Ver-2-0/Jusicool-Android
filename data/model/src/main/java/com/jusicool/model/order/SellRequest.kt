package com.jusicool.model.order

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SellRequest (
    val quantity: Int
)