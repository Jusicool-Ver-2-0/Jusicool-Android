package com.jusicool.entity.price

import com.jusicool.entity.market.Market

data class HoldingWithCurrentPrice(
    val id: Int,
    val market: Market,
    val purchasePrice: Int,
    val quantity: Int,
    val currentPrice: Double
) {
    val priceVariation: Int
        get() = (currentPrice - purchasePrice).toInt()

    val priceVariationPercent: Double
        get() = if (purchasePrice != 0) (priceVariation.toDouble() / purchasePrice) * 100 else 0.0

    val totalValue: Int
        get() = (currentPrice * quantity).toInt()

    val totalVariation: Int
        get() = priceVariation * quantity
}
