package com.jusicool.model.chart

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ChartResponse (
    @Json(name = "market") val market: String,
    @Json(name = "trade_price") val tradePrice: Double,
    @Json(name = "change") val change: String,
    @Json(name = "change_rate") val changeRate: Double,
    @Json(name = "opening_price") val openingPrice: Double,
    @Json(name = "high_price") val highPrice: Double,
    @Json(name = "low_price") val lowPrice: Double
)