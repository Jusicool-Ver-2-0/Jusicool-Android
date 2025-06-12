package com.jusicool.utils

/**
 * 숫자를 퍼센트(%) 형식으로 반환
 * ex: +23.2%, -1.0%
 */

fun Double.formatPercent(): String = String.format("%+.1f%%", this)
fun Float.formatPercent(): String = String.format("%+.1f%%", this)
fun Int.formatPercent(): String = String.format("%+.1f%%", this.toFloat())
fun Long.formatPercent(): String = String.format("%+.1f%%", this.toFloat())