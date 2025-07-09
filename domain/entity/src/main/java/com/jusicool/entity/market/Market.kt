package com.jusicool.entity.market

import com.jusicool.utils.isValidCryptoMarketCode
import com.jusicool.utils.isValidStockMarketCode

data class Market(
    val id: Int,
    val koreanName: String,
    val englishName: String,
    val market: String,
    val marketType: MarketType
) {
    init {
        require(koreanName.isNotBlank()) { "한글 이름은 비어 있을 수 없습니다." }
        require(englishName.isNotBlank()) { "영문 이름은 비어 있을 수 없습니다." }
        require(market.isNotBlank()) { "마켓 정보는 비어 있을 수 없습니다." }

        when (marketType) {
            MarketType.CRYPTO -> require(market.isValidCryptoMarketCode()) { "잘못된 코인 마켓 코드입니다: $market" }
            MarketType.STOCK -> require(market.isValidStockMarketCode()) { "잘못된 주식 마켓 코드입니다: $market" }
        }
    }

    fun isCrypto(): Boolean = marketType == MarketType.CRYPTO

    fun isStock(): Boolean = marketType == MarketType.STOCK
}

enum class MarketType {
    CRYPTO,
    STOCK
}
