package com.jusicool.repository

import com.jusicool.entity.koreaInvestment.AssetsCurrentPrice
import com.jusicool.entity.koreaInvestment.CandleChartEntity
import com.jusicool.model.koreaInvestment.toCandleEntity
import com.jusicool.model.mapper.koreaInvestment.toEntity
import com.jusicool.network.datasource.koreaInvestment.KoreaInvestmentDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        stockCodes: List<String>
    ): Flow<List<AssetsCurrentPrice>> = flow {
        val result = mutableListOf<AssetsCurrentPrice>()
        stockCodes.forEach { code ->
            dataSource.getStockCurrentPrice(marketDivCode, code)
                .map { it.toEntity(code) }
                .collect { result.add(it) }
        }
        emit(result.toList())
        delay(400)
    }
}
