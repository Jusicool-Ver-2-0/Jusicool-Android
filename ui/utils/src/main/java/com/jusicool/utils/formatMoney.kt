package com.jusicool.utils;

import java.text.NumberFormat
import java.util.Locale

fun Int.formatMoney(): String {
    return NumberFormat.getNumberInstance(Locale.KOREA).format(this)
}