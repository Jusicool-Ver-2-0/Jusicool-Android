package com.jusicool.entity

data class ChartModel(
    val market: String,
    val tradePrice: Double,
    val change: String,
    val changeRate: Double,
    val openingPrice: Double,
    val highPrice: Double,
    val lowPrice: Double
)