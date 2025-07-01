package com.jusicool.network.datasource.koreaInvestment

import StockCandleResponse
import StockPriceResponse
import com.jusicool.model.koreaInvestment.StockMinutePriceResponse
import com.jusicool.network.BuildConfig
import com.jusicool.network.api.KoreaInvestmentApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KoreaInvestmentDataSourceImpl @Inject constructor(
    private val api: KoreaInvestmentApi
) : KoreaInvestmentDataSource {

    override fun getStockOrder(
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): Flow<StockCandleResponse> = performApiRequest {
        api.getStockOrder(
            appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
            appSecret = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            trId = "FHKST01010100",
            custType = "P",

            condMrktDivCode = "J",
            pwDataIncuYn = "N",
            inputIsCd = inputIsCd,
            inputHour1 = inputHour1,
            inputDate1 = inputDate1,
        )
    }

    override fun getMinutePrice(
        inputIsCd: String,
        inputHour1: String,
        trCont: String,
    ): Flow<Pair<String, StockMinutePriceResponse>> = performApiRequest {
        val response = api.getMinutePrice(
            appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
            appSecret = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            trId = "FHKST03010200",
            custType = "P",
            trCont = trCont,

            condMrktDivCode = "J",
            pwDataIncuYn = "N",
            inputIsCd = inputIsCd,
            inputHour1 = inputHour1,
            etcClsCode = "00"
        )
        Pair(response.headers()["tr_cont"] ?: "", response.body()!!) // 이렇게 만들 수 있어
    }

    override fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String
    ): Flow<StockPriceResponse> = performApiRequest {
        api.getStockCurrentPrice(
            appKey = BuildConfig.KOREAINVESTMENT_API_KEY,
            appSecret = BuildConfig.KOREAINVESTMENT_APP_SECRET,
            trId = "FHKST01010100",
            custType = "P",

            marketDivCode = marketDivCode,
            stockCode = stockCode
        )
    }
}
