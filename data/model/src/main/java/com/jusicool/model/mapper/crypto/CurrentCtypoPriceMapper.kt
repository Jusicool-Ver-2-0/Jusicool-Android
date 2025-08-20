package com.jusicool.model.mapper.crypto

import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.model.crypto.CurrentCryptoPriceResponse

fun CurrentCryptoPriceResponse.toEntity(): AssetsCurrentPrice =
    AssetsCurrentPrice(
        market = market,
        currentPrice = tradePrice,
        priceDifference = 0.0, // TODO: 임시 값 
        priceDifferenceRate = 0.0
    )