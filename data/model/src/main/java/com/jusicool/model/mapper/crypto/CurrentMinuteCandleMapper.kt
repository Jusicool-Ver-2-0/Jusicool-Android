package com.jusicool.model.mapper.crypto

import com.jusicool.entity.price.MinuteCandleEntity
import com.jusicool.model.crypto.CurrentMinuteCandleResponse
import com.jusicool.utils.parseUpbitDateTime

fun CurrentMinuteCandleResponse.toModel(): MinuteCandleEntity =
    MinuteCandleEntity(
        dateTime = parseUpbitDateTime(candleDateTimeKst),
        openPrice = this.openingPrice,
        highPrice = this.highPrice,
        lowPrice = this.lowPrice,
        closePrice = this.tradePrice,
        volume = 0.0 // TODO: 임시값 확인
    )