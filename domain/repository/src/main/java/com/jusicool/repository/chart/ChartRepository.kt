package com.jusicool.repository.chart

import com.jusicool.model.chart.ChartResponse
import kotlinx.coroutines.flow.Flow

interface ChartRepository {
    suspend fun getChart(): Flow<List<ChartResponse>>
}