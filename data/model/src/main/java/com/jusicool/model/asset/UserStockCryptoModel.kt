package com.jusicool.model.asset

data class UserStockCryptoModel(
    val logoUrl: String,
    val type: AssetType,
    val name: String,
    val amount: Int,
    val price: Int,
    val priceVariation: Int,
    val priceVariationPercent: Double
)

enum class AssetType {
    STOCK, CRYPTO
}