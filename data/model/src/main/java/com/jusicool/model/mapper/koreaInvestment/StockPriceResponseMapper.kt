package com.jusicool.model.mapper.koreaInvestment

import StockPriceResponse
import com.jusicool.entity.price.AssetsCurrentPrice

fun StockPriceResponse.toEntity(market: String): AssetsCurrentPrice =
    AssetsCurrentPrice(
        market = market,
        currentPrice = data.currentPrice.toDoubleOrNull() ?: 0.0,
        priceDifference = data.priceDifference.toDoubleOrNull() ?: 0.0,
        priceDifferenceRate = data.priceDifferenceRate.toDoubleOrNull() ?: 0.0
    )
