package com.jusicool.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun parseDateTime(date: String, time: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    return LocalDateTime.parse(date + time.padStart(6, '0'), formatter)
}
