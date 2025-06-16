package com.jusicool.entity.koreaInvestment

data class StockCandleEntity(
    val dateTime: String,
    val openPrice: Long,
    val closePrice: Long,
    val highPrice: Long,
    val lowPrice: Long,
    val volume: Long
) {
    init {
        require(openPrice > 0) { "시작가는 0보다 커야 합니다." }
        require(closePrice > 0) { "마감가는 0보다 커야 합니다." }
        require(highPrice >= listOf(openPrice, closePrice, lowPrice).max()) { "최고가는 open/close/low 중 가장 높아야 합니다." }
        require(lowPrice <= listOf(openPrice, closePrice, highPrice).min()) { "최저가는 open/close/high 중 가장 낮아야 합니다." }
        require(volume >= 0) { "거래량은 음수가 될 수 없습니다." }
    }
}
