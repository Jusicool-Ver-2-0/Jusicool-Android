package com.jusicool.network.datasource.chart

import com.jusicool.model.chart.ChartResponse
import kotlinx.coroutines.flow.Flow

interface ChartDataSource {
    suspend fun getChart(markets: String): Flow<List<ChartResponse>>
}