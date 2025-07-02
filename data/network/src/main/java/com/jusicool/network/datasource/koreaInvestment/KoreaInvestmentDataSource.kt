package com.jusicool.network.datasource.koreaInvestment

import StockCandleResponse
import StockPriceResponse
import com.jusicool.model.koreaInvestment.StockMinutePriceResponse
import kotlinx.coroutines.flow.Flow

interface KoreaInvestmentDataSource {

    fun getStockOrder(
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): Flow<StockCandleResponse>

    fun getMinutePrice(
        inputIsCd: String,
        inputHour1: String,
        trCont: String,
    ): Flow<StockMinutePriceResponse>

    fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String,
    ): Flow<StockPriceResponse>
}
