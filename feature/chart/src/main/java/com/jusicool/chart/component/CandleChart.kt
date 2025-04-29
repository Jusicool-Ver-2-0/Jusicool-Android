package com.jusicool.chart.component

import androidx.compose.ui.graphics.PathEffect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.model.chart.CandleChartModel
import java.text.NumberFormat

@Composable
fun CandleChart(
    modifier: Modifier = Modifier,
    candles: List<CandleChartModel>
) {
    JusicoolTheme { colors, typography ->
        val totalHeight = 270
        val numberOfLabels = 5

        val listState = rememberLazyListState()

        val visibleCandles = remember(
            listState.firstVisibleItemIndex,
            listState.layoutInfo.visibleItemsInfo,
            candles
        ) {
            val start = listState.firstVisibleItemIndex
            val end = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: start
            if (candles.isEmpty() || start >= candles.size) candles
            else candles.subList(start.coerceAtLeast(0), (end + 1).coerceAtMost(candles.size))
        }

        val maxHigh = visibleCandles.maxOfOrNull { it.shadowHigh } ?: 0
        val minLow = visibleCandles.minOfOrNull { it.shadowLow } ?: 0

        val priceStep = (maxHigh - minLow) / (numberOfLabels - 1)
        val priceLabels = List(numberOfLabels) { index ->
            priceStep * index + minLow
        }.reversed()

        val lastVisibleCandle = run {
            val lastItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastItem?.let {
                visibleCandles.getOrNull(it.index - listState.firstVisibleItemIndex)
            }
        }

        val chartLineColor = when {
            lastVisibleCandle == null -> colors.gray300
            lastVisibleCandle.close > lastVisibleCandle.open -> colors.chartPriceIncreased
            lastVisibleCandle.close < lastVisibleCandle.open -> colors.chartPriceDecreased
            else -> colors.gray300
        }

        val formattedClosePrice = lastVisibleCandle?.let {
            NumberFormat.getNumberInstance().format(it.close)
        } ?: ""

        LaunchedEffect(candles.size) {
            if (candles.isNotEmpty()) {
                listState.scrollToItem(candles.size - 1)
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .height(totalHeight.dp)
        ) {
            Box(modifier = Modifier.weight(6f)) {
                LazyRow(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    contentPadding = PaddingValues(end = 24.dp),
                    state = listState
                ) {
                    items(candles) { candle ->
                        val candleTop =
                            ((maxHigh - candle.shadowHigh).toFloat() / (maxHigh - minLow)) * totalHeight
                        val candleHeight =
                            ((candle.shadowHigh - candle.shadowLow).toFloat() / (maxHigh - minLow)) * totalHeight

                        Column(
                            modifier = Modifier.height(totalHeight.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(candleTop.dp))

                            CandleStick(
                                open = candle.open,
                                close = candle.close,
                                shadowHigh = candle.shadowHigh,
                                shadowLow = candle.shadowLow,
                                height = candleHeight
                            )

                            Spacer(modifier = Modifier.height((totalHeight - candleTop - candleHeight).dp))
                        }
                    }
                }

                Canvas(modifier = Modifier.matchParentSize()) {
                    lastVisibleCandle?.let { candle ->
                        val y = ((maxHigh - candle.close).toFloat() / (maxHigh - minLow)) * size.height

                        drawLine(
                            color = chartLineColor,
                            start = Offset(0f, y),
                            end = Offset(size.width+24, y),
                            strokeWidth = 2f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f),) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    priceLabels.forEach { price ->
                        Text(
                            text = NumberFormat.getNumberInstance().format(price),
                            color = colors.gray500,
                            style = typography.label
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .offset(y = (lastVisibleCandle?.let {
                                ((maxHigh - it.close).toFloat() / (maxHigh - minLow)) * totalHeight - 8f
                            } ?: 0f).dp)
                            .background(color = colors.white)
                            .border(
                                width = 0.5.dp,
                                color = chartLineColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 3.dp)
                    ) {
                        Text(
                            text = formattedClosePrice,
                            color = chartLineColor,
                            style = typography.label
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CandleChartPreview() {
    val mockCandles = listOf(
        CandleChartModel(open = 65000, close = 65500, shadowHigh = 65800, shadowLow = 64800),
        CandleChartModel(open = 65500, close = 66000, shadowHigh = 66300, shadowLow = 65200),
        CandleChartModel(open = 66000, close = 67000, shadowHigh = 67200, shadowLow = 65800),
        CandleChartModel(open = 67000, close = 67500, shadowHigh = 67800, shadowLow = 66600),
        CandleChartModel(open = 67500, close = 68500, shadowHigh = 69000, shadowLow = 67300),
        CandleChartModel(open = 68500, close = 67800, shadowHigh = 68800, shadowLow = 67500),
        CandleChartModel(open = 67800, close = 68000, shadowHigh = 68400, shadowLow = 67600),
        CandleChartModel(open = 68000, close = 67400, shadowHigh = 68200, shadowLow = 67000),
        CandleChartModel(open = 67400, close = 68000, shadowHigh = 68400, shadowLow = 67200),
        CandleChartModel(open = 68000, close = 69000, shadowHigh = 69500, shadowLow = 67800),
        CandleChartModel(open = 69000, close = 68800, shadowHigh = 69300, shadowLow = 68500),
        CandleChartModel(open = 68800, close = 68700, shadowHigh = 69000, shadowLow = 68200),
        CandleChartModel(open = 68700, close = 69000, shadowHigh = 69200, shadowLow = 68500),
        CandleChartModel(open = 69000, close = 70000, shadowHigh = 70500, shadowLow = 68800),
        CandleChartModel(open = 70000, close = 71000, shadowHigh = 71500, shadowLow = 69800),
        CandleChartModel(open = 71000, close = 72000, shadowHigh = 72500, shadowLow = 70700),
        CandleChartModel(open = 72000, close = 70500, shadowHigh = 72300, shadowLow = 70000),
        CandleChartModel(open = 70500, close = 69500, shadowHigh = 70800, shadowLow = 69000),
        CandleChartModel(open = 69500, close = 70000, shadowHigh = 70200, shadowLow = 69300),
        CandleChartModel(open = 70000, close = 69800, shadowHigh = 70100, shadowLow = 69000),
        CandleChartModel(open = 69800, close = 69000, shadowHigh = 69900, shadowLow = 68500),
        CandleChartModel(open = 69000, close = 68800, shadowHigh = 69400, shadowLow = 68400),
        CandleChartModel(open = 68800, close = 68000, shadowHigh = 69000, shadowLow = 67800),
        CandleChartModel(open = 68000, close = 68300, shadowHigh = 68500, shadowLow = 67800),
        CandleChartModel(open = 68300, close = 68100, shadowHigh = 68600, shadowLow = 67500),
        CandleChartModel(open = 68100, close = 69000, shadowHigh = 69300, shadowLow = 67900),
        CandleChartModel(open = 69000, close = 69200, shadowHigh = 69500, shadowLow = 68800),
        CandleChartModel(open = 69200, close = 69800, shadowHigh = 70000, shadowLow = 69000),
        CandleChartModel(open = 69800, close = 69300, shadowHigh = 69900, shadowLow = 68800),
        CandleChartModel(open = 69300, close = 69500, shadowHigh = 69700, shadowLow = 69000),
        CandleChartModel(open = 69500, close = 69000, shadowHigh = 69600, shadowLow = 68500),
        CandleChartModel(open = 69000, close = 68400, shadowHigh = 69200, shadowLow = 68000),
        CandleChartModel(open = 68400, close = 67500, shadowHigh = 68600, shadowLow = 67000),
        CandleChartModel(open = 67500, close = 66800, shadowHigh = 67800, shadowLow = 66500),
        CandleChartModel(open = 66800, close = 67200, shadowHigh = 67500, shadowLow = 66600),
        CandleChartModel(open = 67200, close = 66500, shadowHigh = 67300, shadowLow = 66200),
        CandleChartModel(open = 66500, close = 67000, shadowHigh = 67300, shadowLow = 66000),
        CandleChartModel(open = 67000, close = 67500, shadowHigh = 67800, shadowLow = 66700),
        CandleChartModel(open = 67500, close = 68000, shadowHigh = 68200, shadowLow = 67000),
        CandleChartModel(open = 68000, close = 67800, shadowHigh = 68500, shadowLow = 67500),
        CandleChartModel(open = 67800, close = 68200, shadowHigh = 68400, shadowLow = 67600),
        CandleChartModel(open = 68200, close = 68800, shadowHigh = 69000, shadowLow = 68000),
        CandleChartModel(open = 68800, close = 68500, shadowHigh = 69000, shadowLow = 68200),
        CandleChartModel(open = 68500, close = 69000, shadowHigh = 69200, shadowLow = 68300),
        CandleChartModel(open = 69000, close = 70000, shadowHigh = 70500, shadowLow = 68800),
        CandleChartModel(open = 70000, close = 70500, shadowHigh = 71000, shadowLow = 69800),
        CandleChartModel(open = 70500, close = 70200, shadowHigh = 70800, shadowLow = 70000),
        CandleChartModel(open = 70200, close = 71000, shadowHigh = 71200, shadowLow = 70000),
        CandleChartModel(open = 71000, close = 72000, shadowHigh = 72500, shadowLow = 70800),
        CandleChartModel(open = 72000, close = 72500, shadowHigh = 72800, shadowLow = 71800),
        CandleChartModel(open = 72500, close = 73000, shadowHigh = 73500, shadowLow = 72000),
        CandleChartModel(open = 73000, close = 73500, shadowHigh = 74000, shadowLow = 72800),
        CandleChartModel(open = 73500, close = 73200, shadowHigh = 73800, shadowLow = 73000),
        CandleChartModel(open = 73200, close = 72800, shadowHigh = 73500, shadowLow = 72500),
        CandleChartModel(open = 72800, close = 72200, shadowHigh = 73000, shadowLow = 72000),
        CandleChartModel(open = 72200, close = 71500, shadowHigh = 72500, shadowLow = 71000),
        CandleChartModel(open = 71500, close = 71800, shadowHigh = 72000, shadowLow = 71200),
        CandleChartModel(open = 71800, close = 71000, shadowHigh = 72000, shadowLow = 70500),
        CandleChartModel(open = 71000, close = 70500, shadowHigh = 71200, shadowLow = 70000),
        CandleChartModel(open = 70500, close = 69800, shadowHigh = 70700, shadowLow = 69500),
        CandleChartModel(open = 69800, close = 69000, shadowHigh = 70000, shadowLow = 68500),
        CandleChartModel(open = 69000, close = 68800, shadowHigh = 69200, shadowLow = 68500),
        CandleChartModel(open = 68800, close = 68200, shadowHigh = 69000, shadowLow = 68000),
        CandleChartModel(open = 68200, close = 67800, shadowHigh = 68500, shadowLow = 67500),
        CandleChartModel(open = 67800, close = 67000, shadowHigh = 68000, shadowLow = 66800),
        CandleChartModel(open = 67000, close = 66000, shadowHigh = 67200, shadowLow = 65800),
        CandleChartModel(open = 66000, close = 65500, shadowHigh = 66200, shadowLow = 65200),
        CandleChartModel(open = 65500, close = 65000, shadowHigh = 65800, shadowLow = 64500),
        CandleChartModel(open = 65000, close = 64500, shadowHigh = 65200, shadowLow = 64000),
        CandleChartModel(open = 64500, close = 64000, shadowHigh = 64800, shadowLow = 63800),
        CandleChartModel(open = 64000, close = 64200, shadowHigh = 64500, shadowLow = 63800),
        CandleChartModel(open = 64200, close = 64800, shadowHigh = 65000, shadowLow = 64000),
        CandleChartModel(open = 64800, close = 65200, shadowHigh = 65500, shadowLow = 64500),
        CandleChartModel(open = 65200, close = 66000, shadowHigh = 66200, shadowLow = 65000),
        CandleChartModel(open = 66000, close = 65800, shadowHigh = 66300, shadowLow = 65500),
        CandleChartModel(open = 65800, close = 65500, shadowHigh = 66000, shadowLow = 65000),
        CandleChartModel(open = 65500, close = 64800, shadowHigh = 65700, shadowLow = 64500),
        CandleChartModel(open = 64800, close = 64000, shadowHigh = 65000, shadowLow = 63800)
    )

    CandleChart(candles = mockCandles)
}