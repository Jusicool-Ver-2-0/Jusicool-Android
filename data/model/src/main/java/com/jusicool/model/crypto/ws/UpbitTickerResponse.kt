package com.jusicool.model.crypto.ws

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpbitTickerResponse(
    @Json(name = "code") val market: String,
    @Json(name = "trade_price") val tradePrice: Double,
    @Json(name = "change") val change: String,
    @Json(name = "signed_change_price") val signedChangePrice: Double,
    @Json(name = "signed_change_rate") val signedChangeRate: Double,
    @Json(name = "acc_trade_volume_24h") val accTradeVolume24h: Double? = null,
    @Json(name = "acc_trade_price_24h") val accTradePrice24h: Double? = null,
    @Json(name = "trade_timestamp") val tradeTimestamp: Long? = null
)