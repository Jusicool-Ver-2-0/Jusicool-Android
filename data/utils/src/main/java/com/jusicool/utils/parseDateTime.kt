package com.jusicool.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun parseDateTime(date: String, time: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return LocalDateTime.parse(date + time.padStart(6, '0'), formatter)
}

fun parseDateTime(dateTime: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return LocalDateTime.parse(dateTime, formatter)
}

fun parseUpbitDateTime(dateTime: String): LocalDateTime {
    return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

fun parseStockDateTime(date: String, time: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    return LocalDateTime.parse(date + time.padStart(6, '0'), formatter)
}
