package com.jusicool.repository

import StockPriceResponse
import com.jusicool.entity.koreaInvestment.CandleChartEntity
import kotlinx.coroutines.flow.Flow

interface KoreaInvestmentRepository {

    suspend fun getStockOrder(
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): Flow<List<CandleChartEntity>>

    suspend fun getMinutePrice(
        inputIsCd: String,
        inputHour1: String,
    ): Flow<List<CandleChartEntity>>

    suspend fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String
    ): Flow<StockPriceResponse>
}
