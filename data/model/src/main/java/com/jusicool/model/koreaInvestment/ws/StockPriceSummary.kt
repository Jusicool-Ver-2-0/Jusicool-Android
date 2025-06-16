package com.jusicool.model.koreaInvestment.ws

data class StockPriceSummary(
    val stockCode: String,
    val currentPrice: Int,
    val prevDiffPrice: Int,
    val prevDiffRatio: Double,
    val prevDiffSign: Int
) {
    companion object {
        fun fromRawData(rawData: String): StockPriceSummary? {
            // rawData 예시: "005930^093730^0^71900^...^-72000^5^-100.00^..."
            val parts = rawData.split("^")
            if (parts.size < 60) return null

            val stockCode = parts[0]
            val currentPrice = parts[3].toIntOrNull() ?: return null
            val prevDiffPrice = parts[56].toIntOrNull() ?: 0
            val prevDiffSign = parts[57].toIntOrNull() ?: 0
            val prevDiffRatio = parts[58].toDoubleOrNull() ?: 0.0

            return StockPriceSummary(
                stockCode = stockCode,
                currentPrice = currentPrice,
                prevDiffPrice = prevDiffPrice,
                prevDiffRatio = prevDiffRatio,
                prevDiffSign = prevDiffSign
            )
        }
    }
}
