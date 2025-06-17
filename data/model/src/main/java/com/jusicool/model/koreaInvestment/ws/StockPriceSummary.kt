package com.jusicool.model.koreaInvestment.ws

data class StockPriceSummary(
    val stockCode: String,      // 종목코드
    val currentPrice: Double,   // 현재가 (매도호가1로 대체 가능)
    val prevDiffPrice: Double,  // 전일 대비 가격 차이
    val prevDiffRatio: Double,  // 전일 대비 비율
    val prevDiffSign: Int,      // 전일 대비 부호 (1: 상한, 2: 상승, 3: 보합, 4: 하한, 5: 하락)
    val time: String?           // 시간 문자열 (BSOP_HOUR)
) {
    companion object {
        fun fromRawData(rawData: String): StockPriceSummary? {
            val parts = rawData.split("^")
            if (parts.size < 59) return null  // 필수 필드 최소 개수 체크

            val stockCode = parts[0]
            val time = parts[1]   // 1번 인덱스가 BSOP_HOUR (시간)
            val currentPrice = parts[3].toDoubleOrNull() ?: return null  // 매도호가1
            val prevDiffPrice = parts[56].toDoubleOrNull() ?: 0.0       // 예상 체결 대비
            val prevDiffSign = parts[57].toIntOrNull() ?: 0             // 예상 체결 대비 부호
            val prevDiffRatio = parts[58].toDoubleOrNull() ?: 0.0       // 예상 체결 전일 대비율

            return StockPriceSummary(
                stockCode = stockCode,
                currentPrice = currentPrice,
                prevDiffPrice = prevDiffPrice,
                prevDiffRatio = prevDiffRatio,
                prevDiffSign = prevDiffSign,
                time = time
            )
        }
    }
}
