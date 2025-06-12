package com.jusicool.utils

fun Int.toSignedText(): String = String.format("%+d", this)
fun Long.toSignedText(): String = String.format("%+d", this)
fun Float.toSignedText(): String = String.format("%+.1f", this)
fun Double.toSignedText(): String = String.format("%+.1f", this)
