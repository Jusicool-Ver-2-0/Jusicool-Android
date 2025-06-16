package com.jusicool.entity.koreaInvestment

/**
 * 보유 주식 정보 (도메인 모델)
 */
data class OwnedStockEntity(
    val stockCode: String,          // 종목 코드 (MKSC_SHRN_ISCD)
    val quantity: Int,              // 보유 주식 수
    val currentPrice: Long,         // 현재가 (STCK_PRPR)
    val priceChange: Long,          // 전일 대비 금액 (PRDY_VRSS)
    val priceChangeRate: Double,    // 전일 대비율 (PRDY_CTRT)
    val priceChangeSign: String     // 전일 대비 부호 ("1"=상승, "2"=하락, "3"=보합)
) {
    init {
        require(quantity >= 0) { "보유 주식 수는 0 이상이어야 합니다." }
        require(currentPrice >= 0) { "현재가는 0 이상이어야 합니다." }
        require(priceChange >= 0) { "전일 대비 금액은 0 이상이어야 합니다." }
        require(priceChangeSign in listOf("1", "2", "3")) {
            "전일 대비 부호는 \"1\"(상승), \"2\"(하락), \"3\"(보합) 중 하나여야 합니다."
        }
    }

    /**
     * 상승 여부
     */
    val isRising: Boolean
        get() = priceChangeSign == "1"

    /**
     * 하락 여부
     */
    val isFalling: Boolean
        get() = priceChangeSign == "2"

    /**
     * 보합 여부
     */
    val isFlat: Boolean
        get() = priceChangeSign == "3"

    /**
     * 사용자 친화적인 부호 문자열 (UI 계층에서 사용 가능)
     */
    val changeDirectionText: String
        get() = when (priceChangeSign) {
            "1" -> "+"
            "2" -> "-"
            "3" -> "+"
            else -> "?"
        }
}
