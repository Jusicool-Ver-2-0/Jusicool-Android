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

    /** 현재 보유 종목이 코인인지 여부 */
    fun isCrypto(): Boolean = market.marketType == MarketType.CRYPTO

    /** 현재 보유 종목이 주식인지 여부 */
    fun isStock(): Boolean = market.marketType == MarketType.STOCK

    /** 총 평가 금액 (수량 * 가격) */
    fun totalValue(): Int = quantity * price
}
