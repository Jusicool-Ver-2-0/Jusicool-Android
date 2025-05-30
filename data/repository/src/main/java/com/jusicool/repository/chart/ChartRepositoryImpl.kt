package com.jusicool.repository.chart

import com.jusicool.network.datasource.chart.ChartDataSource
import com.jusicool.model.chart.ChartResponse
import kotlinx.coroutines.flow.Flow
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