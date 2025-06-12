package com.jusicool.model.crypto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentCryptoPriceResponse (
    val market: String,
    @Json(name = "trade_price") val tradePrice: Double
)