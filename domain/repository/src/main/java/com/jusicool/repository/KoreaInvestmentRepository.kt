package com.jusicool.repository

import com.jusicool.entity.price.MinuteCandleEntity
import com.jusicool.entity.price.AssetsCurrentPrice
import kotlinx.coroutines.flow.Flow

interface KoreaInvestmentRepository {

    fun getStockOrder(
        inputIsCd: String,
        inputHour1: String,
        inputDate1: String,
    ): Flow<List<MinuteCandleEntity>>

    fun getMinutePrice(
        inputIsCd: String,
        inputHour1: String,
        trCont: String
    ): Flow<List<MinuteCandleEntity>>

    fun getStockCurrentPrice(
        marketDivCode: String,
        markets: List<String>
    ): Flow<List<AssetsCurrentPrice>>
}
