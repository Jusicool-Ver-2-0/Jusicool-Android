package com.jusicool.entity.crypto

data class MinuteCandleModel (
    val candleDateTimeKst: String,
    val openingPrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val tradePrice: Double
)