package com.jusicool.entity.price

data class MinuteCandleModel(
    val candleDateTimeKst: String,
    val openingPrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val tradePrice: Double
) {
    init {
        require(openingPrice >= 0) { "시가는 0 이상이어야 합니다." }
        require(highPrice >= 0) { "고가는 0 이상이어야 합니다." }
        require(lowPrice >= 0) { "저가는 0 이상이어야 합니다." }
        require(tradePrice >= 0) { "종가는 0 이상이어야 합니다." }

        require(highPrice >= lowPrice) { "고가는 저가보다 크거나 같아야 합니다." }
        require(highPrice >= openingPrice && highPrice >= tradePrice) { "고가는 시가와 종가보다 크거나 같아야 합니다." }
        require(lowPrice <= openingPrice && lowPrice <= tradePrice) { "저가는 시가와 종가보다 작거나 같아야 합니다." }
    }

    /** 종가 - 시가 */
    val priceChange: Double
        get() = tradePrice - openingPrice

    /** 등락률 (%, 소수점 기준) */
    val priceChangeRatio: Double
        get() = if (openingPrice == 0.0) 0.0 else (priceChange / openingPrice) * 100

    /** 양봉 여부 */
    val isBullish: Boolean
        get() = tradePrice > openingPrice

    /** 음봉 여부 */
    val isBearish: Boolean
        get() = tradePrice < openingPrice
}