package com.jusicool.model.chart

data class CandleChartModel(
    val open: Int,
    val close: Int,
    val shadowHigh: Int,
    val shadowLow: Int
)