package com.jusicool.model.mapper.crypto

import com.jusicool.entity.crypto.CurrentMinuteCandleModel
import com.jusicool.model.crypto.CurrentMinuteCandleResponse

fun CurrentMinuteCandleResponse.toModel(): CurrentMinuteCandleModel =
    CurrentMinuteCandleModel(
        candleDateTimeKst = this.candleDateTimeKst,
        openingPrice = this.openingPrice,
        highPrice = this.highPrice,
        lowPrice = this.lowPrice,
        tradePrice = this.tradePrice
    )