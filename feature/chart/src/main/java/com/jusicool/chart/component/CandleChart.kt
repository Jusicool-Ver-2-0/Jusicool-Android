package com.jusicool.chart.component

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.chart.viewModel.uiState.GetCurrentMinuteCandleUiState
import com.jusicool.chart.viewModel.uiState.GetMinuteCandleUiState
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.entity.crypto.CurrentMinuteCandleModel
import com.jusicool.entity.crypto.MinuteCandleModel
import com.jusicool.utils.Logger
import java.text.NumberFormat

@Composable
fun CandleChart(
    modifier: Modifier = Modifier,
    candles: List<MinuteCandleModel>,
    currentCandlesData: GetCurrentMinuteCandleUiState,
    market: String,
    onRefresh: (String) -> Unit
) {
    JusicoolTheme { colors, typography ->
        val totalHeight = 270
        val numberOfLabels = 5

        val listState = rememberLazyListState()
        var isFirstLoad by remember { mutableStateOf(true) }
        var previousSize by remember { mutableStateOf(candles.size) }

        // 현재 화면에 보이는 캔들 리스트 계산
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

        // 현재 캔들 데이터에서 최신 캔들 하나 가져오기
        val currentCandle = if (currentCandlesData is GetCurrentMinuteCandleUiState.Success && currentCandlesData.candles.isNotEmpty()) {
            currentCandlesData.candles.first()
        } else null

        // 현재 캔들이 화면에 보이는지 여부 확인
        val isCurrentCandleVisible = listState.layoutInfo.visibleItemsInfo
            .any { it.index == candles.lastIndex + 1 }

        // 현재 캔들이 보이면 현재 캔들도 포함, 아니면 그냥 visibleCandles만 사용
        val visibleWithCurrent = if (isCurrentCandleVisible && currentCandle != null) {
            visibleCandles + currentCandle
        } else {
            visibleCandles
        }

        // 보이는 캔들들 중 최고가 계산
        val maxHigh = visibleWithCurrent.maxOfOrNull { candle ->
            when (candle) {
                is MinuteCandleModel -> candle.highPrice
                is CurrentMinuteCandleModel -> candle.highPrice
                else -> 0.0
            }
        } ?: 0.0

        // 보이는 캔들들 중 최저가 계산
        val minLow = visibleWithCurrent.minOfOrNull { candle ->
            when (candle) {
                is MinuteCandleModel -> candle.lowPrice
                is CurrentMinuteCandleModel -> candle.lowPrice
                else -> Double.MAX_VALUE
            }
        } ?: 0.0

        // 가격 라벨 간격 계산
        val priceStep = (maxHigh - minLow) / (numberOfLabels - 1)

        // Y축에 표시될 가격 라벨 리스트 생성 (높은 가격이 위로 오도록 뒤집음)
        val priceLabels = List(numberOfLabels) { index ->
            priceStep * index + minLow
        }.reversed()

        // 기준 캔들 선택: 현재 캔들이 보이면 그것을, 아니면 마지막 보이는 캔들을 사용
        val referenceCandle = when {
            isCurrentCandleVisible && currentCandle != null -> currentCandle
            else -> visibleCandles.lastOrNull()
        }

        val chartLineColor = when (referenceCandle) {
            is MinuteCandleModel -> when {
                referenceCandle.tradePrice > referenceCandle.openingPrice -> colors.chartPriceIncreased
                referenceCandle.tradePrice < referenceCandle.openingPrice -> colors.chartPriceDecreased
                else -> colors.gray300
            }
            is CurrentMinuteCandleModel -> when {
                referenceCandle.tradePrice > referenceCandle.openingPrice -> colors.chartPriceIncreased
                referenceCandle.tradePrice < referenceCandle.openingPrice -> colors.chartPriceDecreased
                else -> colors.gray300
            }
            else -> colors.gray300
        }

        val formattedClosePrice = when (referenceCandle) {
            is MinuteCandleModel -> NumberFormat.getNumberInstance().format(referenceCandle.tradePrice)
            is CurrentMinuteCandleModel -> NumberFormat.getNumberInstance().format(referenceCandle.tradePrice)
            else -> ""
        }

        LaunchedEffect(candles.size) {
            if (isFirstLoad && candles.isNotEmpty()) {
                listState.scrollToItem(candles.size - 1)
                isFirstLoad = false
            } else if (candles.size > previousSize) {
                val addedCount = candles.size - previousSize
                val newIndex = listState.firstVisibleItemIndex + addedCount
                listState.scrollToItem(newIndex.coerceAtMost(candles.size - 1))
            }
            previousSize = candles.size
        }

        var isInitialLoad by remember { mutableStateOf(true) }
        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemIndex }
                .collect { index ->
                    if (index == 0 && !isInitialLoad) {
                        onRefresh(market)
                    }
                    if (isInitialLoad) {
                        isInitialLoad = false
                    }
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
                        val candleTop = ((maxHigh - candle.highPrice) / (maxHigh - minLow)) * totalHeight
                        val candleHeight = ((candle.highPrice - candle.lowPrice) / (maxHigh - minLow)) * totalHeight
                        val adjustedCandleHeight = candleHeight.coerceAtLeast(1.0)

                        Column(
                            modifier = Modifier.height(totalHeight.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(candleTop.dp))

                            CandleStick(
                                open = candle.openingPrice,
                                close = candle.tradePrice,
                                shadowHigh = candle.highPrice,
                                shadowLow = candle.lowPrice,
                                height = adjustedCandleHeight
                            )

                            Spacer(modifier = Modifier.height((totalHeight - candleTop - candleHeight).dp))
                        }
                    }

                    if (currentCandle != null) {
                        val currentCandleTop = ((maxHigh - currentCandle.highPrice) / (maxHigh - minLow)) * totalHeight
                        val currentCandleHeight = ((currentCandle.highPrice - currentCandle.lowPrice) / (maxHigh - minLow)) * totalHeight
                        val adjustedCandleHeight = currentCandleHeight.coerceAtLeast(1.0)

                        item {
                            Column(
                                modifier = Modifier.height(totalHeight.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(currentCandleTop.dp))

                                CurrentCandleStick(
                                    height = adjustedCandleHeight,
                                    currentCandlesData = currentCandlesData
                                )

                                Spacer(modifier = Modifier.height((totalHeight - currentCandleTop - currentCandleHeight).dp))
                            }
                        }
                    }
                }

                Canvas(modifier = Modifier.matchParentSize()) {
                    referenceCandle?.let { candle ->
                        val price = getTradePrice(candle)
                        val y = (((maxHigh - price) / (maxHigh - minLow)) * size.height).toFloat()

                        drawLine(
                            color = chartLineColor,
                            start = Offset(0f, y),
                            end = Offset(size.width + 24f, y),
                            strokeWidth = 2f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f)) {
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
                    val yOffset = referenceCandle?.let {
                        val tradePrice = getTradePrice(it)
                        val range = maxHigh - minLow
                        if (range == 0.0) {
                            0f
                        } else {
                            ((maxHigh - tradePrice) / range * totalHeight).toFloat() - 8f
                        }
                    } ?: 0f

                    Box(
                        modifier = Modifier
                            .offset(y = yOffset.dp)
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

fun getTradePrice(candle: Any): Double = when (candle) {
    is MinuteCandleModel -> candle.tradePrice
    is CurrentMinuteCandleModel -> candle.tradePrice
    else -> 0.0
}

@Preview(showBackground = true)
@Composable
fun CandleChartPreview() {
    val mockCandles = listOf(
        MinuteCandleModel(
            candleDateTimeKst = "2025-06-13 12:00",
            openingPrice = 30000.0,
            highPrice = 31000.0,
            lowPrice = 29500.0,
            tradePrice = 30500.0
        ),
        MinuteCandleModel(
            candleDateTimeKst = "2025-06-13 12:01",
            openingPrice = 30500.0,
            highPrice = 31200.0,
            lowPrice = 30400.0,
            tradePrice = 31000.0
        ),
        MinuteCandleModel(
            candleDateTimeKst = "2025-06-13 12:02",
            openingPrice = 31000.0,
            highPrice = 31500.0,
            lowPrice = 30900.0,
            tradePrice = 31300.0
        )
    )

    val mockCurrentCandlesData = GetCurrentMinuteCandleUiState.Success(
        candles = listOf(
            CurrentMinuteCandleModel(
                candleDateTimeKst = "",
                openingPrice = 31300.0,
                highPrice = 31600.0,
                lowPrice = 31200.0,
                tradePrice = 31500.0,
            )
        )
    )

    CandleChart(
        candles = mockCandles,
        currentCandlesData = mockCurrentCandlesData,
        market = "",
        onRefresh = {}
    )
}
