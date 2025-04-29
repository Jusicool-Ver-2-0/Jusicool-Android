package com.jusicool.chart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.model.chart.ChartPriceModel

@Composable
fun PriceBarChart(
    modifier: Modifier = Modifier,
    price: ChartPriceModel
) {
    val maxBarWidth = 180.dp

    JusicoolTheme { colors, typography ->
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(72.dp)
                        .width(1.dp)
                        .background(color = colors.main)
                )

                Column(modifier = modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(((price.dayMinPrice / price.dayMaxPrice.toFloat()) * maxBarWidth.value).dp)
                                .height(20.dp)
                                .background(
                                    color = Color(0xFFA8BCFA),
                                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 8.dp)
                                )
                        )
                        Text(
                            text = "1일 최저가 ${"%,d".format(price.dayMinPrice)}원",
                            color = colors.gray400,
                            style = typography.label
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(maxBarWidth)
                                .height(20.dp)
                                .background(
                                    color = colors.main,
                                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 8.dp)
                                )
                        )
                        Text(
                            text = "1일 최고가 ${"%,d".format(price.dayMaxPrice)}원",
                            color = colors.gray600,
                            style = typography.label
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(72.dp)
                        .width(1.dp)
                        .background(color = colors.main)
                )

                Column(modifier = modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(((price.yearMinPrice / price.yearMaxPrice.toFloat()) * maxBarWidth.value).dp)
                                .height(20.dp)
                                .background(
                                    color = Color(0xFFA8BCFA),
                                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 8.dp)
                                )
                        )
                        Text(
                            text = "1년 최저가 ${"%,d".format(price.yearMinPrice)}원",
                            color = colors.gray400,
                            style = typography.label
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(maxBarWidth)
                                .height(20.dp)
                                .background(
                                    color = colors.main,
                                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 8.dp)
                                )
                        )
                        Text(
                            text = "1년 최고가 ${"%,d".format(price.yearMaxPrice)}원",
                            color = colors.gray600,
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
fun PriceBarChartPreview() {
    PriceBarChart(
        price = ChartPriceModel(
            dayMinPrice = 566772,
            dayMaxPrice = 600449,
            yearMinPrice = 426236,
            yearMaxPrice = 801212,
            dayOpen = 596772,
            dayClose = 598824,
            tradingVolume = 2912,
            tradingPrice = 23400000000
        )
    )
}
