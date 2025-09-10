package com.jusicool.entity.crypto

data class UpbitTickerModel(
    val market: String,
    val price: Double,
    val changeSign: String,
    val changePrice: Double,
    val changeRatePercent: Double,
    val ts: Long? = null,
)