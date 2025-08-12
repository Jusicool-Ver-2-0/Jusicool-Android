package com.jusicool.model.crypto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DayCandleResponse(
    @Json(name = "change_price") val changePrice: Double,
)