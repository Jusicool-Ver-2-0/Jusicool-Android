package com.jusicool.entity.holding

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.utils.isValidCryptoMarketCode
import com.jusicool.utils.isValidStockMarketCode

data class HoldingModel(
    val id: Int,
    val market: Market,
    val quantity: Int,
    val price: Int
) {
    init {
        when (market.marketType) {
            MarketType.CRYPTO -> {
                require(market.market.isValidCryptoMarketCode()) {
                    "잘못된 CRYPTO 마켓 코드 형식: ${market.market}"
                }
            }

            MarketType.STOCK -> {
                // 주식: 6자리 숫자만 허용
                require(market.market.isValidStockMarketCode()) {
                    "잘못된 STOCK 마켓 코드 형식: ${market.market}"
                }
            }
        }
    }

    val isCrypto: Boolean
        get() = market.marketType == MarketType.CRYPTO

    val isStock: Boolean
        get() = market.marketType == MarketType.STOCK

    val totalValue: Int
        get() = quantity * price
}
