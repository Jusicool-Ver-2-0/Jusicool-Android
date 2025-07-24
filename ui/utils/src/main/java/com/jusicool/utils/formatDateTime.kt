package com.jusicool.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter


private val MonthDayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M월 d일")

/**
 * LocalDate를 "M월 d일" 포맷의 문자열로 변환합니다.
 */
fun LocalDate.toMonthDayString(): String = this.format(MonthDayFormatter)