package com.jusicool.network.datasource.koreaInvestment

import StockCandleResponse
import StockPriceResponse
import com.jusicool.model.koreaInvestment.StockMinutePriceResponse
import com.jusicool.network.BuildConfig
import com.jusicool.network.api.KoreaInvestmentApi

class KoreaInvestmentDataSourceImpl(
    private val api: KoreaInvestmentApi
) : KoreaInvestmentDataSource {

    override suspend fun getStockOrder(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): StockCandleResponse {
        return api.getStockOrder(
            appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
            appSecret = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            trId = "FHKST01010100",
            trCont = null,
            custType = "P",

            condMrktDivCode = "J",
            pwDataIncuYn = "N",
            inputIsCd = inputIsCd,
            inputHour1 = inputHour1,
            inputDate1 = inputDate1,
        )
    }

    override suspend fun getMinutePrice(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
    ): StockMinutePriceResponse {
        return api.getMinutePrice(
            appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
            appSecret = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            trId = "FHKST01010100",
            trCont = null,
            custType = "P",

            condMrktDivCode = "J",
            pwDataIncuYn = "N",
            inputIsCd = inputIsCd,
            inputHour1 = inputHour1,
            etcClsCode = "00"
        )
    }

    override suspend fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String
    ): StockPriceResponse {
        return api.getStockCurrentPrice(
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
