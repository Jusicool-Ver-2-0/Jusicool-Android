package com.jusicool.model.koreaInvestment.ws

import android.util.Log

data class StockPriceSummary(
    val stockCode: String,
    val currentPrice: Double,
    val prevDiffPrice: Double,
    val prevDiffRatio: Double,
    val prevDiffSign: Int,
    val time: String
) {
    companion object {
        fun parseStockPriceSummary(rawData: String): StockPriceSummary? {
            Log.d("Raw input:", rawData)

            val parts = rawData.split("|")
            if (parts.size < 4) {
                return null
            }

            val body = parts[3]

            val splitData = body.split("^")

            return StockPriceSummary(
                stockCode = splitData[0],
                time = splitData[1],
                currentPrice = splitData[3].toDoubleOrNull() ?: 0.0,
                prevDiffPrice = splitData[51].toDoubleOrNull() ?: 0.0,
                prevDiffSign = splitData[53].toIntOrNull() ?: 0,
                prevDiffRatio = splitData[54].toDoubleOrNull() ?: 0.0
            )
        }
    }
}
