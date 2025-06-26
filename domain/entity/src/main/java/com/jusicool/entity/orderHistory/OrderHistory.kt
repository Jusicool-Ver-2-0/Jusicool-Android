package com.jusicool.entity.orderHistory

data class OrderHistory(
    val id: Int,
    val market: String,
    val orderType: OrderType,
    val reserveType: ReserveType,
    val status: OrderStatus,
    val quantity: Int,
    val executePrice: Int?,
    val reservePrice: Int?,
) {
    init {
        require(quantity > 0) { "주문 수량은 0보다 커야 합니다." }
        require(executePrice == null || executePrice >= 0) { "체결 가격은 null이거나 0 이상이어야 합니다." }
        require(reservePrice == null || reservePrice >= 0) { "예약 가격은 null이거나 0 이상이어야 합니다." }
        require(market.isNotBlank()) { "마켓 정보는 비어 있을 수 없습니다." }
    }

    fun isBuyOrder(): Boolean = orderType == OrderType.BUY

    fun isSellOrder(): Boolean = orderType == OrderType.SELL

    fun isCompleted(): Boolean = status == OrderStatus.COMPLETED

    fun calculateTotalExecutePrice(): Int = quantity * (executePrice ?: 0)

    fun calculateTotalReservePrice(): Int = quantity * (reservePrice ?: 0)
}

enum class OrderType {
    BUY,
    SELL,
}

enum class ReserveType {
    RESERVE,    // 예약 주문
    IMMEDIATE,  // 즉시 주문
}

enum class OrderStatus {
    COMPLETED,   // 주문 완료
    PENDING,     // 대기 중
    CANCELED,    // 취소됨
}
