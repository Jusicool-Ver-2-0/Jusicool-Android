package com.jusicool.model.mapper.crypto

import com.jusicool.entity.price.MinuteCandleEntity
import com.jusicool.model.crypto.MinuteCandleResponse
import com.jusicool.utils.parseUpbitDateTime

fun MinuteCandleResponse.toModel(): MinuteCandleEntity =
    MinuteCandleEntity(
        dateTime = parseUpbitDateTime(candleDateTimeKst),
        openPrice = openingPrice,
        highPrice = highPrice,
        lowPrice = lowPrice,
        closePrice = tradePrice,
        volume = 0.0 // TODO: 임시 volumn값
    )