package com.jusicool.chart.component

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jusicool.chart.viewModel.uiState.GetCurrentMinuteCandleUiState
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.formatMoney

@Composable
fun ChartPrice(currentMinuteCandleData: GetCurrentMinuteCandleUiState) {
    JusicoolTheme { colors, typography ->
        when (currentMinuteCandleData) {
            is GetCurrentMinuteCandleUiState.Success -> {
                val tradePrice = currentMinuteCandleData.candles.firstOrNull()?.tradePrice?.toLong() ?: 0L

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