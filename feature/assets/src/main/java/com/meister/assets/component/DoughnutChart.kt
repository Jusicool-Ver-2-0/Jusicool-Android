package com.meister.assets.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DoughnutChart(
    modifier: Modifier = Modifier,
    data: List<Pair<Float, Color>>,
    strokeWidth: Float = 60f,
    gapAngle: Float = 1f
) {
    val total = data.sumOf { it.first.toDouble() }.toFloat()
    val proportions = data.map { 360 * it.first / total }

    Canvas(modifier = modifier) {
        val inset = strokeWidth / 2
        val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
        val topLeft = Offset(inset, inset)

        var startAngle = -90f

        for (i in data.indices) {
            val sweep = proportions[i] - gapAngle

            drawArc(
                color = data[i].second,
                startAngle = startAngle + gapAngle / 2,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )

            startAngle += proportions[i]
        }
    }
}

@Preview
@Composable
private fun DoughnutChartPreview() {
    val colors = listOf(
        15f to Color(0xFFB0C4DE), // LightSteelBlue
        25f to Color(0xFFFFB6C1), // LightPink
        35f to Color(0xFF98FB98), // PaleGreen
        10f to Color(0xFFFFFF99), // LightYellow
        15f to Color(0xFFDAA6F0)  // LightPurple (custom)
    )

    DoughnutChart(
        data = colors,
        modifier = Modifier
            .size(240.dp)
    )
}
