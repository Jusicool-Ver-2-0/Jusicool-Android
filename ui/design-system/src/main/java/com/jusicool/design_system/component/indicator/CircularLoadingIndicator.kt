package com.jusicool.design_system.component.indicator

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
fun CircularLoadingIndicator(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp,
    size: Dp = 40.dp,
) {
    JusicoolTheme { colors, _ ->
        val infiniteTransition = rememberInfiniteTransition(label = "loading")

        val angle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1100, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "angle"
        )

        val sweep by infiniteTransition.animateFloat(
            initialValue = 30f,
            targetValue = 300f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "sweep"
        )

        Canvas(
            modifier = modifier.size(size)
        ) {
            drawArc(
                color = colors.main,
                startAngle = angle,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularLoadingIndicatorPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularLoadingIndicator(
            size = 64.dp,
            strokeWidth = 6.dp
        )
    }
}
