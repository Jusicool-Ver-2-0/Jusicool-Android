package com.jusicool.model.order

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MonthlyRateResponse(
    @Json(name = "monthly_rate") val monthlyRate: Double,
    val markets: List<RateByMarket>,
)

@JsonClass(generateAdapter = true)
data class RateByMarket(
    val market: String,
    @Json(name = "korean_name") val koreanName: String,
    val rate: Double,
    val proceed: Int,
    val day: String,
)