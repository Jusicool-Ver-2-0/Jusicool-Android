package com.jusicool.model.chart

data class ChartPriceModel(
    val dayMinPrice: Int,
    val dayMaxPrice: Int,
    val yearMinPrice: Int,
    val yearMaxPrice: Int,
    val dayOpen: Int,
    val dayClose: Int,
    val tradingVolume: Int,
    val tradingPrice: Long
)