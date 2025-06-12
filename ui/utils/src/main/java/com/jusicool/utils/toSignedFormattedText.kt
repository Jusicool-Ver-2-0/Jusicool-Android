package com.jusicool.utils

import java.text.NumberFormat
import java.util.Locale

fun Int.toSignedFormattedText(): String {
    val formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(kotlin.math.abs(this))
    val sign = if (this >= 0) "+" else "-"
    return "$sign$formatted"
}

fun Long.toSignedFormattedText(): String {
    val formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(kotlin.math.abs(this))
    val sign = if (this >= 0) "+" else "-"
    return "$sign$formatted"
}

fun Float.toSignedFormattedText(): String {
    val formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(kotlin.math.abs(this))
    val sign = if (this >= 0) "+" else "-"
    return "$sign$formatted"
}

fun Double.toSignedFormattedText(): String {
    val formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(kotlin.math.abs(this))
    val sign = if (this >= 0) "+" else "-"
    return "$sign$formatted"
}
