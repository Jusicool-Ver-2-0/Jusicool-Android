package com.jusicool.repository

import StockPriceResponse
import com.jusicool.entity.koreaInvestment.CandleChartEntity

interface KoreaInvestmentRepository {

    suspend fun getStockOrder(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): List<CandleChartEntity>

    suspend fun getMinutePrice(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
    ): List<CandleChartEntity>

    suspend fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String
    ): StockPriceResponse
}
