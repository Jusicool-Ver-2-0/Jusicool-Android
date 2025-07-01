package com.jusicool.repository

import StockPriceResponse
import com.jusicool.entity.koreaInvestment.CandleChartEntity
import kotlinx.coroutines.flow.Flow

interface KoreaInvestmentRepository {

    fun getStockOrder(
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): Flow<List<CandleChartEntity>>

    fun getMinutePrice(
        inputIsCd: String,
        inputHour1: String,
        trCont: String
    ): Flow<Pair<String, List<CandleChartEntity>>>

    fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String
    ): Flow<StockPriceResponse>
}
