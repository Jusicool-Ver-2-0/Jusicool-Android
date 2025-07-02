package com.jusicool.entity.koreaInvestment

/**
 * 보유 주식 정보 (도메인 모델)
 */
data class OwnedStockEntity(
    val stockCode: String,          // 종목 코드 (MKSC_SHRN_ISCD)
    val stockName: String,          // 종목 이름 (예: "삼성전자")
    val stockLogoUrl: String?,      // 종목 로고 URL 또는 리소스 ID (nullable)
    val quantity: Int,              // 보유 주식 수
    val currentPrice: Long,         // 현재가 (STCK_PRPR)
    val priceChange: Long,          // 전일 대비 금액 (PRDY_VRSS)
    val priceChangeRate: Double,    // 전일 대비율 (PRDY_CTRT)
    val priceChangeSign: String     // 전일 대비 부호 ("1"=상승, "2"=하락, "3"=보합)
) {
    init {
        require(stockCode.isNotBlank()) { "종목 코드는 비어 있을 수 없습니다." }
        require(stockName.isNotBlank()) { "종목 이름은 비어 있을 수 없습니다." }
        require(quantity >= 0) { "보유 주식 수는 0 이상이어야 합니다." }
        require(currentPrice >= 0) { "현재가는 0 이상이어야 합니다." }
        require(priceChange >= 0) { "전일 대비 금액은 0 이상이어야 합니다." }
        require(priceChangeSign in listOf("1", "2", "3")) {
            "전일 대비 부호는 \"1\"(상승), \"2\"(하락), \"3\"(보합) 중 하나여야 합니다."
        }
    }

    val isRising: Boolean
        get() = priceChangeSign == "1"

    val isFalling: Boolean
        get() = priceChangeSign == "2"

    val isFlat: Boolean
        get() = priceChangeSign == "3"

    val changeDirectionText: String
        get() = when (priceChangeSign) {
            "1", "3" -> "+" // 상승 또는 보합
            "2" -> "-"
            else -> "?" // 도달 불가
        }
}
