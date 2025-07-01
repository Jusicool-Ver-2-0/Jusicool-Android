package com.jusicool.network.datasource.koreaInvestment

import StockCandleResponse
import StockPriceResponse
import com.jusicool.model.koreaInvestment.AccessKeyRequest
import com.jusicool.model.koreaInvestment.AccessTokenResponse
import com.jusicool.model.koreaInvestment.StockMinutePriceResponse
import com.jusicool.model.koreaInvestment.WebSocketAccessKeyResponse
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
    ): Flow<StockMinutePriceResponse>

    fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String,
    ): Flow<StockPriceResponse>
}
