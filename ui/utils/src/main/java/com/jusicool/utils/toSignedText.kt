package com.jusicool.utils


fun Int.toSignedText(): String = when {
    this > 0 -> "+$this"
    this < 0 -> "$this"
    else -> "0"
}

fun Long.toSignedText(): String = when {
    this > 0 -> "+$this"
    this < 0 -> "$this"
    else -> "0"
}

fun Float.toSignedText(): String = when {
    this > 0 -> "+$this"
    this < 0 -> "$this"
    else -> "0"
}

fun Double.toSignedText(): String = when {
    this > 0 -> "+$this"
    this < 0 -> "$this"
    else -> "0"
}
