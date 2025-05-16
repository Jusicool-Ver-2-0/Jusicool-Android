package com.meister.assets.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.formatMoney
import com.meister.assets.component.DoughnutChart
import com.meister.assets.viewModel.MonthlyIncomeViewModel
import com.meister.assets.viewModel.uiState.MonthlyIncomeUiState
import com.school_of_company.design_system.icon.ClarityArrowLineIcon
import kotlin.random.Random


@Composable
internal fun MonthlyIncomeRoute(
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit,
    monthlyIncomeViewModel: MonthlyIncomeViewModel = hiltViewModel(),
) {
    val uiState by monthlyIncomeViewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {}
        uiState.errorMessage != null -> {}
        else -> {
            MonthlyIncomeScreen(
                modifier = modifier,
                uiState = uiState,
                navigateToBack = navigateToBack,
            )
        }
    }
}

@Composable
private fun MonthlyIncomeScreen(
    modifier: Modifier = Modifier,
    uiState: MonthlyIncomeUiState,
    navigateToBack: () -> Unit,
) {
    val scrollState = rememberScrollState()

    JusicoolTheme { colors, typography ->
        val stockDistribution = transformData(uiState.ownedStocks)

        Column(
            modifier = modifier
                .background(colors.white)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            JusicoolTopBar(
                startIcon = {
                    ClarityArrowLineIcon(modifier = Modifier.clickable(onClick = navigateToBack))
                },
                betweenText = "내 자산",
                endIcon = {
                    Spacer(modifier = Modifier.size(24.dp))
                },
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {

                Column {
                    Text(
                        text = "${uiState.myMoney.formatMoney()}원",
                        style = typography.titleMedium,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    val changeColor =
                        if (uiState.moneyChangeFromLastMonth < 0) colors.chartPriceDecreased else colors.chartPriceIncreased

                    val annotatedString = buildAnnotatedString {
                        append("지난 달 보다 ")

                        withStyle(style = SpanStyle(color = changeColor)) {
                            append(uiState.moneyChangeFromLastMonth.formatMoney())
                            append("원 ")
                        }

                        append("늘었어요")
                    }

                    Text(
                        text = annotatedString,
                        style = typography.bodySmall
                    )
                }

                Column {
                    Text(
                        text = "주문 가능 금액",
                        style = typography.bodyMedium,
                        color = colors.gray600,
                    )

                    Text(
                        text = "${uiState.availableOrderAmount.formatMoney()}원",
                        style = typography.titleSmall,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Column {
                        Text(
                            text = "투자 금액",
                            style = typography.bodyMedium,
                            color = colors.gray600,
                        )

                        Text(
                            text = "${uiState.investedAmount.formatMoney()}원",
                            style = typography.titleSmall,
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        DoughnutChart(
                            modifier = Modifier.size(240.dp),
                            data = stockDistribution.map { (it.percent to it.color) },
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        stockDistribution.forEach {
                            OwnedStocksBar(
                                modifier = Modifier.fillMaxWidth(),
                                corpName = it.name,
                                purchasedShares = it.count,
                                holdingRatio = it.percent,
                                stockColor = it.color,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MonthlyIncomeScreenPreview() {
    MonthlyIncomeScreen(
        uiState = MonthlyIncomeUiState(
            isLoading = false,
            myMoney = 1000000,
            moneyChangeFromLastMonth = 50,
            availableOrderAmount = 200,
            investedAmount = 800,
            ownedStocks = listOf("dqdw" to 100, "dqdw" to 100),
            errorMessage = null
        ),
        navigateToBack = {},
    )
}

@Composable
private fun OwnedStocksBar(
    modifier: Modifier = Modifier,
    corpName: String,
    purchasedShares: Int,
    holdingRatio: Float,
    stockColor: Color,
) {
    JusicoolTheme { colors, typography ->

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(
                    Modifier
                        .size(24.dp)
                        .background(stockColor, CircleShape),
                )
                Column {
                    Text(
                        text = corpName,
                        style = typography.subTitle
                    )

                    Text(
                        text = "${purchasedShares.formatMoney()}원",
                        style = typography.subTitle
                    )
                }
            }

            Text(
                text = "${holdingRatio}%",
                style = typography.subTitle
            )
        }
    }
}


data class Item(
    val name: String,
    val count: Int,
    val percent: Float,
    val color: Color
)

private fun transformData(input: List<Pair<String, Int>>): List<Item> {
    val grouped = input.groupBy({ it.first }, { it.second })
        .mapValues { it.value.sum() }

    val total = grouped.values.sum().takeIf { it > 0 } ?: 1

    return grouped.map { (name, count) ->
        val percent = count.toFloat() / total * 100
        val hue = Random.nextFloat() * 360f
        val saturation = 0.5f
        val value = 0.8f
        val color = Color.hsv(hue, saturation, value)

        Item(name = name, count = count, percent = percent, color = color)
    }
}