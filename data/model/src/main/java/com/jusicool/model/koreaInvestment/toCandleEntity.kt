package com.jusicool.model.koreaInvestment

import StockMinuteCandle
import com.jusicool.entity.price.MinuteCandleEntity
import com.jusicool.utils.parseStockDateTime

fun StockMinuteCandle.toCandleEntity(): MinuteCandleEntity? {
    return try {
        val dateTime = parseStockDateTime(date, time)
        MinuteCandleEntity(
            dateTime = dateTime,
            openPrice = openingPrice.toLong().toDouble(),
            closePrice = currentPrice.toLong().toDouble(),
            highPrice = highPrice.toLong().toDouble(),
            lowPrice = lowPrice.toLong().toDouble(),
            volume = transactionVolume.toLong().toDouble(),
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun StockMinuteDetail.toCandleEntity(): MinuteCandleEntity? {
    return try {
        val dateTime = parseStockDateTime(baseDate, baseHour)
        MinuteCandleEntity(
            dateTime = dateTime,
            openPrice = openPrice.toLong().toDouble(),
            closePrice = currentPrice.toLong().toDouble(),
            highPrice = highPrice.toLong().toDouble(),
            lowPrice = lowPrice.toLong().toDouble(),
            volume = volume.toLong().toDouble(),
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}