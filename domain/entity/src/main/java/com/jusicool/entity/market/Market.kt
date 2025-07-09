package com.jusicool.entity.market

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
    }

    fun isCrypto(): Boolean = marketType == MarketType.CRYPTO

    fun isStock(): Boolean = marketType == MarketType.STOCK
}

enum class MarketType {
    CRYPTO,
    STOCK
}
