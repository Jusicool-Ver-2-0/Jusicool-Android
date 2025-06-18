package com.jusicool.repository

import StockCandleResponse
import StockPriceResponse
import com.jusicool.model.koreaInvestment.AccessKeyRequest
import com.jusicool.model.koreaInvestment.AccessTokenResponse
import com.jusicool.model.koreaInvestment.StockMinutePriceResponse
import com.jusicool.model.koreaInvestment.WebSocketAccessKeyResponse
import com.jusicool.network.datasource.koreaInvestment.KoreaInvestmentDataSource
import javax.inject.Inject

class KoreaInvestmentRepositoryImpl @Inject constructor(
    private val dataSource: KoreaInvestmentDataSource
) : KoreaInvestmentRepository {

    override suspend fun getStockOrder(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): StockCandleResponse {
        return dataSource.getStockOrder(
            condMrktDivCode,
            inputIsCd,
            inputHour1,
            inputDate1,
        )
    }

    override suspend fun getMinutePrice(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
    ): StockMinutePriceResponse {
        return dataSource.getMinutePrice(
            condMrktDivCode,
            inputIsCd,
            inputHour1,
        )
    }

    override suspend fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String
    ): StockPriceResponse {
        return dataSource.getStockCurrentPrice(
            marketDivCode,
            stockCode
        )
    }
}
