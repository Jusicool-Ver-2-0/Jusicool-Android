package com.jusicool.network.datasource.koreaInvestment

import StockCandleResponse
import StockPriceResponse
import com.jusicool.model.koreaInvestment.AccessKeyRequest
import com.jusicool.model.koreaInvestment.AccessTokenResponse
import com.jusicool.model.koreaInvestment.StockMinutePriceResponse
import com.jusicool.model.koreaInvestment.WebSocketAccessKeyResponse
import com.jusicool.network.BuildConfig
import com.jusicool.network.api.KoreaInvestmentApi

class KoreaInvestmentDataSourceImpl(
    private val api: KoreaInvestmentApi
) : KoreaInvestmentDataSource {

    override suspend fun getAccessToken(request: AccessKeyRequest): AccessTokenResponse {
        return api.getAccessToken(request)
    }

    override suspend fun getWebSocketAccessToken(request: AccessKeyRequest): WebSocketAccessKeyResponse {
        return api.getWebSocketAccessToken(request)
    }

    override suspend fun getStockOrder(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
        pwDataIncuYn: String,
        fakeTickIncuYn: String?
    ): StockCandleResponse {
        return api.getStockOrder(
            contentType = "application/json; charset=utf-8",
            appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
            appSecret = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            trId = "FHKST01010100",
            trCont = null,
            custType = "P",

            condMrktDivCode = condMrktDivCode,
            inputIsCd = inputIsCd,
            inputHour1 = inputHour1,
            inputDate1 = inputDate1,
            pwDataIncuYn = pwDataIncuYn,
            fakeTickIncuYn = fakeTickIncuYn
        )
    }

    override suspend fun getMinutePrice(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
        pwDataIncuYn: String,
        etcClsCode: String
    ): StockMinutePriceResponse {
        return api.getMinutePrice(
            contentType = "application/json; charset=utf-8",
            appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
            appSecret = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            trId = "FHKST01010100",
            trCont = null,
            custType = "P",

            condMrktDivCode = condMrktDivCode,
            inputIsCd = inputIsCd,
            inputHour1 = inputHour1,
            pwDataIncuYn = pwDataIncuYn,
            etcClsCode = etcClsCode
        )
    }

    override suspend fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String
    ): StockPriceResponse {
        return api.getStockCurrentPrice(
            contentType = "application/json; charset=utf-8",
            appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
            appSecret = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            trId = "FHKST01010100",
            trCont = null,
            custType = "P",

            marketDivCode = marketDivCode,
            stockCode = stockCode
        )
    }
}
