package com.jusicool.utils

fun String.isValidCryptoMarketCode(): Boolean {
    return this.matches(Regex("^[A-Z]{2,5}-[A-Z0-9]{1,15}$"))
}

fun String.isValidStockMarketCode(): Boolean {
    return this.matches(Regex("^\\d{4,6}[A-Z0-9]{0,2}$"))
}