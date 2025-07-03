package com.jusicool.model.holding

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HoldingResponse(
    val id: Int,
    val marketDto: MarketDto,
    val quantity: Int,
    val price: Int
)

@JsonClass(generateAdapter = true)
data class MarketDto(
    val id: Int,
    @Json(name = "korean_name") val koreanName: String,
    @Json(name = "english_name") val englishName: String?,
    val market: String,
    @Json(name = "market_type")val marketType: String
)