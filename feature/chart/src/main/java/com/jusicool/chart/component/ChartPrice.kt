package com.jusicool.chart.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jusicool.chart.viewModel.uiState.GetCurrentMinuteCandleUiState
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.formatMoney

@Composable
fun ChartPrice(currentMinuteCandleData: GetCurrentMinuteCandleUiState) {
    JusicoolTheme { colors, typography ->
        when (currentMinuteCandleData) {
            is GetCurrentMinuteCandleUiState.Success -> {
                val tradePrice = currentMinuteCandleData.candles.firstOrNull()?.closePrice?.toLong() ?: 0L

                Text(
                    text = tradePrice.formatMoney(),
                    color = colors.black,
                    style = typography.titleMedium
                )
            }
            else -> {}
        }
    }
}