package com.jusicool.model.crypto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentMinuteCandleResponse (
    @Json(name = "candle_date_time_kst") val candleDateTimeKst: String,
    @Json(name = "opening_price") val openingPrice: Double,
    @Json(name = "high_price") val highPrice: Double,
    @Json(name = "low_price") val lowPrice: Double,
    @Json(name = "trade_price") val tradePrice: Double
)