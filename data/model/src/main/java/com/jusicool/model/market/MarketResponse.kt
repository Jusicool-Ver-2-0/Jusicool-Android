package com.jusicool.model.market

import com.squareup.moshi.Json

data class MarketResponse(
    val id: Int,
    @Json(name = "korean_name") val koreanName: String,
    @Json(name = "english_name") val englishName: String?,
    val market: String,
    @Json(name = "market_type") val marketType: String,
)