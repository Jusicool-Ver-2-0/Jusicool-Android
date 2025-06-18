package com.jusicool.repository

import StockPriceResponse
import com.jusicool.entity.koreaInvestment.CandleChartEntity
import com.jusicool.model.koreaInvestment.toCandleEntity
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
    ): List<CandleChartEntity> {
        return dataSource.getStockOrder(
            condMrktDivCode,
            inputIsCd,
            inputHour1,
            inputDate1
        ).candles.mapNotNull { it.toCandleEntity() }
    }

    override suspend fun getMinutePrice(
        condMrktDivCode: String,
        inputIsCd: String,
        inputHour1: String,
    ): List<CandleChartEntity> {
        return dataSource.getMinutePrice(
            condMrktDivCode,
            inputIsCd,
            inputHour1
        ).details.mapNotNull { it.toCandleEntity() }
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
