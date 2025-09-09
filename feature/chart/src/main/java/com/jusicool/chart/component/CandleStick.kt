package com.jusicool.chart.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme
import kotlin.math.max
import kotlin.math.min

@Composable
fun CandleStick(
    modifier: Modifier = Modifier,
    open: Double,
    close: Double,
    shadowHigh: Double,
    shadowLow: Double,
    height: Double
) {
    JusicoolTheme { colors, _ ->
        val bodyColor = when {
            close > open -> colors.chartPriceIncreased
            close < open -> colors.chartPriceDecreased
            else -> colors.gray300
        }

        val bodyW = 9.dp
        val wickW = 1.dp
        val radius = 2.dp
        val range = (shadowHigh - shadowLow).takeIf { it > 0.0 } ?: 1.0

        Box(
            modifier = modifier
                .width(bodyW)
                .height(height.dp)
                .drawBehind {
                    val H = size.height
                    val W = size.width
                    fun y(v: Double): Float =
                        (((shadowHigh - v) / range) * H).toFloat()

                    val yHigh = y(shadowHigh)
                    val yLow = y(shadowLow)
                    val yOpen = y(open)
                    val yClose = y(close)
                    val topBody = min(yOpen, yClose)
                    val botBody = max(yOpen, yClose)

                    val cx = W / 2f
                    val wickPx = wickW.toPx()
                    drawLine(color = bodyColor, start = Offset(cx, yHigh), end = Offset(cx, topBody), strokeWidth = wickPx)
                    drawLine(color = bodyColor, start = Offset(cx, botBody), end = Offset(cx, yLow), strokeWidth = wickPx)

                    val bw = bodyW.toPx()
                    val bh = max(1f, botBody - topBody)
                    drawRoundRect(
                        color = bodyColor,
                        topLeft = Offset(cx - bw / 2f, topBody),
                        size = Size(bw, bh),
                        cornerRadius = CornerRadius(radius.toPx(), radius.toPx())
                    )
                }
        )
    }
}
