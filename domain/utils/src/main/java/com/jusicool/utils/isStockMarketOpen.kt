package com.jusicool.utils

fun isStockMarketOpen(): Boolean {
    val now = java.time.LocalDateTime.now()
    val dayOfWeek = now.dayOfWeek
    val hour = now.hour
    val minute = now.minute

    // 평일 (월~금) 이고, 09:00 ~ 15:30 사이면 장 열림
    return dayOfWeek.value in 1..5 &&
            (hour > 9 || (hour == 9 && minute >= 0)) &&
            (hour < 15 || (hour == 15 && minute <= 30))
}
