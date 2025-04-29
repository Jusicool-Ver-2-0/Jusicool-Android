package com.jusicool.chart.component

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
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
fun CandleStick(
    modifier: Modifier = Modifier,
    open: Int,
    close: Int,
    shadowHigh: Int,
    shadowLow: Int,
    height: Float
) {
    JusicoolTheme { colors, typography ->
        val bodyColor = when {
            close > open -> colors.chartPriceIncreased
            close < open -> colors.chartPriceDecreased
            else -> colors.gray300
        }

        val total = shadowHigh - shadowLow

        val upperShadowHeight = (shadowHigh - maxOf(open, close)).toFloat() / total * height
        val bodyHeight = (kotlin.math.abs(open - close).toFloat() / total) * height
        val lowerShadowHeight = (minOf(open, close) - shadowLow).toFloat() / total * height

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
                    .height(if (bodyHeight < 1f) 1.dp else bodyHeight.dp)
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
}