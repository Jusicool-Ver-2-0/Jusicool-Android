package com.jusicool.repository

import com.example.network.datasource.chart.ChartDataSource
import com.jusicool.entity.ChartModel
import com.jusicool.model.chart.ChartResponse
import com.jusicool.model.mapper.toModel
import com.jusicool.repository.chart.ChartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor(
    private val chartDataSource: ChartDataSource
): ChartRepository {
    override suspend fun getChart(): Flow<List<ChartResponse>> {
        val markets = defaultMarkets.joinToString(",")
        return chartDataSource.getChart(markets)
    }

    companion object {
        private val defaultMarkets = listOf("KRW-BTC", "KRW-ETH", "KRW-XRP")
    }
}