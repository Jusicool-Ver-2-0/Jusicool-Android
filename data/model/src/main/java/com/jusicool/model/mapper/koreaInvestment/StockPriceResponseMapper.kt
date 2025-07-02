package com.jusicool.model.mapper.koreaInvestment

import StockPriceResponse
import com.jusicool.entity.koreaInvestment.AssetsCurrentPrice

fun StockPriceResponse.toEntity(): AssetsCurrentPrice =
    AssetsCurrentPrice(
        currentPrice = this.data.currentPrice.toIntOrNull() ?: 0,
        priceDifference = this.data.priceDifference.toIntOrNull() ?: 0,
        priceDifferenceRate = this.data.priceDifferenceRate.toDoubleOrNull() ?: 0.0
    )
