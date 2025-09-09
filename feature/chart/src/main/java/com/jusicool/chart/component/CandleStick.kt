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

        // 좌표 정규화용 분모(0 나눗셈 방지)
        val range = (shadowHigh - shadowLow).takeIf { it > 0.0 } ?: 1.0

        Box(
            modifier = modifier
                .width(bodyW)
                .height(height.dp)
                .drawBehind {
                    val H = size.height
                    val W = size.width

                    // 가격값 v를 현재 캔버스의 y 픽셀 좌표로 매핑
                    // shadowHigh 가 상단(0f), shadowLow 가 하단(H)에 오도록 선형 변환
                    fun y(v: Double): Float =
                        (((shadowHigh - v) / range) * H).toFloat()

                    // 주요 y 좌표 계산
                    val yHigh = y(shadowHigh) // 윗꼬리 끝
                    val yLow = y(shadowLow)   // 아랫꼬리 끝
                    val yOpen = y(open)       // 시가 위치
                    val yClose = y(close)     // 종가 위치

                    // 몸통의 상/하단 y (작은 값이 위쪽, 큰 값이 아래쪽)
                    val topBody = min(yOpen, yClose)
                    val botBody = max(yOpen, yClose)

                    // 중앙 x, 두께(px)
                    val cx = W / 2f
                    val wickPx = wickW.toPx()

                    // 윗꼬리: 고가부터 몸통 상단까지
                    drawLine(
                        color = bodyColor,
                        start = Offset(cx, yHigh),
                        end = Offset(cx, topBody),
                        strokeWidth = wickPx
                    )

                    // 아랫꼬리: 몸통 하단부터 저가까지
                    drawLine(
                        color = bodyColor,
                        start = Offset(cx, botBody),
                        end = Offset(cx, yLow),
                        strokeWidth = wickPx
                    )

                    // 몸통 사각형
                    val bw = bodyW.toPx()
                    val bh = max(1f, botBody - topBody) // 종가==시가일 때도 최소 1px 보장
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
