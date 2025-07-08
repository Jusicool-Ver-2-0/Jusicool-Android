package com.jusicool.repository

import StockPriceResponse
import com.jusicool.entity.koreaInvestment.CandleChartEntity
import com.jusicool.model.koreaInvestment.toCandleEntity
import com.jusicool.network.datasource.koreaInvestment.KoreaInvestmentDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KoreaInvestmentRepositoryImpl @Inject constructor(
    private val dataSource: KoreaInvestmentDataSource
) : KoreaInvestmentRepository {

    override fun getStockOrder(
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): Flow<List<CandleChartEntity>> {
        return dataSource.getStockOrder(
            inputIsCd,
            inputHour1,
            inputDate1
        ).map { stockCandleResponse ->
            stockCandleResponse.candles.mapNotNull { it.toCandleEntity() }
        }
    }

    override fun getMinutePrice(
        inputIsCd: String,
        inputHour1: String,
        trCont: String
    ): Flow<List<CandleChartEntity>> {
        return dataSource.getMinutePrice(
            inputIsCd,
            inputHour1,
            trCont
        ).map { response ->
            response.details.mapNotNull { it.toCandleEntity() }
        }
    }


    override fun getStockCurrentPrice(
        marketDivCode: String,
        stockCode: String
    ): Flow<StockPriceResponse> {
        return dataSource.getStockCurrentPrice(
            marketDivCode,
            stockCode
        )
    }
}
