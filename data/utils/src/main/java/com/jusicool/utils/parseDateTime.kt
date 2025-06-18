package com.jusicool.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun parseDateTime(date: String, time: String): LocalDateTime {
    // 예: 20240618 + 0930 → 2024-06-18T09:30
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
    return LocalDateTime.parse(date + time.padStart(4, '0'), formatter)
}