package com.example.network.datasource.chart

import com.jusicool.utils.performApiRequest
import com.example.network.api.ChartApi
import com.example.network.util.UpbitRetrofit
import com.jusicool.model.chart.ChartResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChartDataSourceImpl @Inject constructor(
    @UpbitRetrofit private val service: ChartApi
): ChartDataSource {
    override suspend fun getChart(markets: String): Flow<List<ChartResponse>> =
        performApiRequest { service.getChart(markets = markets) }
}