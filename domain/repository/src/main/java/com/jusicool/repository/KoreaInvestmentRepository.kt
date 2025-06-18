package com.jusicool.repository

import com.jusicool.model.koreaInvestment.StockMinutePriceResponse
import StockCandleResponse
import StockPriceResponse

interface KoreaInvestmentRepository {

    suspend fun getStockOrder(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): StockCandleResponse

    suspend fun getMinutePrice(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
    ): StockMinutePriceResponse

    suspend fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String
    ): StockPriceResponse
}
