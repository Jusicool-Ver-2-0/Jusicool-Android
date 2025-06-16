package com.jusicool.model.mapper.crypto

import com.jusicool.entity.crypto.MinuteCandleModel
import com.jusicool.model.crypto.MinuteCandleResponse

fun MinuteCandleResponse.toModel(): MinuteCandleModel =
    MinuteCandleModel(
        candleDateTimeKst = this.candleDateTimeKst,
        openingPrice = this.openingPrice,
        highPrice = this.highPrice,
        lowPrice = this.lowPrice,
        tradePrice = this.tradePrice
    )