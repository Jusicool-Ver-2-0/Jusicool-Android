package com.jusicool.entity.koreaInvestment

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class StockPriceEntity(
    val stockCode: String,
    val currentPrice: Double,
    val priceChange: Double,
    val priceChangeRate: Double,
    val priceChangeSign: String,
    val time: LocalTime? = null
) {
    companion object {
        private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss")

        fun parseTime(timeString: String?): LocalTime? {
            return try {
                timeString?.let { LocalTime.parse(it, TIME_FORMATTER) }
            } catch (e: DateTimeParseException) {
                null
            }
        }
    }

    init {
        require(stockCode.isNotBlank()) { "종목 코드는 비어 있을 수 없습니다." }
        require(currentPrice >= 0) { "현재가는 0 이상이어야 합니다." }
        require(priceChangeSign in listOf("1", "2", "3", "4", "5")) {
            "전일 대비 부호는 \"1\"(상한), \"2\"(상승), \"3\"(보합), \"4\"(하한), \"5\"(하락) 중 하나여야 합니다."
        }
    }

    val isRising: Boolean
        get() = priceChangeSign in listOf("1", "2")

    val isFalling: Boolean
        get() = priceChangeSign in listOf("4", "5")

    val isFlat: Boolean
        get() = priceChangeSign == "3"

    val changeDirectionText: String
        get() = when (priceChangeSign) {
            "1", "2", "3" -> "+"
            "4", "5" -> "-"
            else -> "?"
        }
}
