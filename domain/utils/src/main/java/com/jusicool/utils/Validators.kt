package com.jusicool.utils

fun String.isValidCryptoMarketCode(): Boolean {
    return this.matches(Regex("^[A-Z]{3,4}-[A-Z0-9]{2,10}$"))
}

fun String.isValidStockMarketCode(): Boolean {
    return this.matches(Regex("^\\d{6}$"))
}