package com.jusicool.chart.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jusicool.chart.viewModel.uiState.GetCurrentMinuteCandleUiState
import com.jusicool.design_system.theme.JusicoolTheme
import kotlin.math.abs

@Composable
fun CurrentCandleStick(
    modifier: Modifier = Modifier,
    height: Double,
    currentCandlesData: GetCurrentMinuteCandleUiState
) {
    JusicoolTheme { colors, typography ->
        when(currentCandlesData) {
            is GetCurrentMinuteCandleUiState.Success -> {
                val openingPrice = currentCandlesData.candles.firstOrNull()?.openingPrice ?: 0.0
                val tradePrice = currentCandlesData.candles.firstOrNull()?.tradePrice?: 0.0
                val highPrice = currentCandlesData.candles.firstOrNull()?.highPrice?: 0.0
                val lowPrice = currentCandlesData.candles.firstOrNull()?.lowPrice?: 0.0

                val bodyColor = when {
                    tradePrice > openingPrice -> colors.chartPriceIncreased
                    tradePrice < openingPrice -> colors.chartPriceDecreased
                    else -> colors.gray300
                }

                val total = highPrice - lowPrice

                val upperShadowHeight = ((highPrice - maxOf(openingPrice, tradePrice)) / total * height)
                val bodyHeight = (abs(openingPrice - tradePrice) / total * height).coerceAtLeast(1.0)
                val lowerShadowHeight = ((minOf(openingPrice, tradePrice) - lowPrice) / total * height)

                Column(
                    modifier = modifier.height(height.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    if (upperShadowHeight > 0f) {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(upperShadowHeight.dp)
                                .background(bodyColor)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(9.dp)
                            .height(bodyHeight.dp)
                            .background(bodyColor, shape = RoundedCornerShape(2.dp))
                    )

                    if (lowerShadowHeight > 0f) {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(lowerShadowHeight.dp)
                                .background(bodyColor)
                        )
                    }
                }
            }
            else -> {

            }
        }
    }
}