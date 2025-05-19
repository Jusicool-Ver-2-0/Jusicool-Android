package com.jusicool.usecase.chart

import com.jusicool.repository.chart.ChartRepository
import javax.inject.Inject

class GetChartUseCase @Inject constructor(
    private val chartRepository: ChartRepository
) {
    suspend operator fun invoke() = runCatching {
        chartRepository.getChart()
    }
}