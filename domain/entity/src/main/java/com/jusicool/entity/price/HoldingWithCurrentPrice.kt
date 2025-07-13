package com.jusicool.entity.price

import com.jusicool.entity.market.Market

data class HoldingWithCurrentPrice(
    val id: Int,
    val market: Market,
    val purchasePrice: Int,
    val quantity: Int,
    val currentPrice: Double
) {
    fun priceVariation(): Int = (currentPrice - purchasePrice).toInt()

    fun priceVariationPercent(): Double =
        if (purchasePrice != 0) (priceVariation().toDouble() / purchasePrice) * 100 else 0.0

    fun totalValue(): Int = (currentPrice * quantity).toInt()

    fun totalVariation(): Int = priceVariation() * quantity
}
