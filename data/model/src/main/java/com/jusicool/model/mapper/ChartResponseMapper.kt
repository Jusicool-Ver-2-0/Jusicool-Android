package com.jusicool.model.mapper

import com.jusicool.entity.ChartModel
import com.jusicool.model.chart.ChartResponse

fun ChartResponse.toModel(): ChartModel =
    ChartModel(
        market =this.market,
        tradePrice = this.tradePrice,
        change = this.change,
        changeRate = this.changeRate,
        openingPrice = this.openingPrice,
        highPrice = this.highPrice,
        lowPrice = this.lowPrice
    )