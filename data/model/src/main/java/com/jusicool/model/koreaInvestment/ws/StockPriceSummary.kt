package com.jusicool.model.koreaInvestment.ws

data class StockPriceSummary(
    val stockCode: String,       // MKSC_SHRN_ISCD (0)
    val prevDiffRatio: Double,    // PRDY_CTRT (5)
    val currentPrice: Double,    // STCK_PRPR (2)
    val prevDiffPrice: Double,   // PRDY_VRSS (4)
    val prevDiffSign: Int,       // PRDY_VRSS_SIGN (3)
    val time: String,            // STCK_CNTG_HOUR (1)
) {
    companion object {
        const val STOCK_PRICE_FIELDS_COUNT = 46

        fun parseStockPriceSummary(rawData: String): StockPriceSummary? {
            val splitData = rawData.split("^")
            if (splitData.size < STOCK_PRICE_FIELDS_COUNT) return null

            return try {
                StockPriceSummary(
                    stockCode = splitData[0],
                    time = splitData[1],
                    currentPrice = splitData[2].toDoubleOrNull() ?: 0.0,
                    prevDiffPrice = splitData[4].toDoubleOrNull() ?: 0.0,
                    prevDiffSign = splitData[3].toIntOrNull() ?: 0,
                    prevDiffRatio = splitData[5].toDoubleOrNull() ?: 0.0
                )
            } catch (e: Exception) {
                null
            }
        }
    }
}
