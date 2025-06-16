package com.jusicool.network.datasource.koreaInvestment

import StockCandleResponse
import StockPriceResponse
import com.jusicool.model.koreaInvestment.AccessKeyRequest
import com.jusicool.model.koreaInvestment.AccessTokenResponse
import com.jusicool.model.koreaInvestment.StockMinutePriceResponse
import com.jusicool.model.koreaInvestment.WebSocketAccessKeyResponse

interface KoreaInvestmentDataSource {

    suspend fun getStockOrder(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
        pwDataIncuYn: String,
        fakeTickIncuYn: String? = null
    ): StockCandleResponse

    suspend fun getMinutePrice(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
        pwDataIncuYn: String,
        etcClsCode: String
    ): StockMinutePriceResponse

    suspend fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String,
    ): StockPriceResponse
}
