package com.jusicool.model.market

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarketListResponse(
    @Json(name = "has_next") val hasNext: Boolean,
    val items: List<MarketResponse>
)