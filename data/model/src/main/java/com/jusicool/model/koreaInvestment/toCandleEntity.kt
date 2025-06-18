package com.jusicool.model.koreaInvestment

import StockMinuteCandle
import com.jusicool.entity.koreaInvestment.CandleChartEntity
import com.jusicool.utils.parseDateTime

fun StockMinuteCandle.toCandleEntity(): CandleChartEntity? {
    return try {
        val dateTime = parseDateTime(date, time)
        CandleChartEntity(
            dateTime = dateTime,
            openPrice = openingPrice.toLong(),
            closePrice = currentPrice.toLong(),
            highPrice = highPrice.toLong(),
            lowPrice = lowPrice.toLong(),
            volume = transactionVolume.toLong()
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun StockMinuteDetail.toCandleEntity(): CandleChartEntity? {
    return try {
        val dateTime = parseDateTime(baseDate, baseHour)
        CandleChartEntity(
            dateTime = dateTime,
            openPrice = openPrice.toLong(),
            closePrice = currentPrice.toLong(),
            highPrice = highPrice.toLong(),
            lowPrice = lowPrice.toLong(),
            volume = volume.toLong()
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}