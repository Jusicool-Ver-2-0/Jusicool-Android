package com.jusicool.model.mapper.crypto

import com.jusicool.entity.price.MinuteCandleModel
import com.jusicool.model.crypto.CurrentMinuteCandleResponse

fun CurrentMinuteCandleResponse.toModel(): MinuteCandleModel =
    MinuteCandleModel(
        candleDateTimeKst = this.candleDateTimeKst,
        openingPrice = this.openingPrice,
        highPrice = this.highPrice,
        lowPrice = this.lowPrice,
        tradePrice = this.tradePrice
    )