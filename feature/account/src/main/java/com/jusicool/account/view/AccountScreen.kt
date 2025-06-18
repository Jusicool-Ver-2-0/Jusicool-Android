package com.jusicool.account.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.account.component.AssetList
import com.jusicool.account.component.HoldingNewsCard
import com.jusicool.account.viewModel.uiState.GetAccountUiState
import com.jusicool.account.viewModel.AccountViewModel
import com.jusicool.account.viewModel.uiState.GetCurrentCryptoPriceUiState
import com.jusicool.account.viewModel.uiState.GetHoldingUiState
import com.jusicool.account.viewModel.uiState.GetMonthOrderUiState
import com.jusicool.design_system.R
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.entity.holding.HoldingModel
import com.jusicool.entity.order.OrderModel
import com.jusicool.model.news.HoldingNewsModel
import com.jusicool.usecase.crypto.CurrentCryptoHoldingPrice
import com.jusicool.utils.formatMoney
import com.jusicool.utils.formatPercent
import com.jusicool.utils.toSignedFormattedText
import com.school_of_company.design_system.icon.RightArrowIcon
import kotlinx.collections.immutable.toPersistentList


@Composable
internal fun AccountRoute(
    viewModel: AccountViewModel = hiltViewModel(),
    navigateToChart: (marketCode: String, name: String, type: String, quantity: Int, money: Long, krwBalance: Long) -> Unit
) {
    val accountUiState by viewModel.accountUiState.collectAsStateWithLifecycle()
    val holdingUiState by viewModel.holdingUiState.collectAsStateWithLifecycle()
    val currentCryptoPriceUiState by viewModel.currentCryptoPriceUiState.collectAsStateWithLifecycle()
    val monthOrderUiState by viewModel.monthOrderUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAccount()
        viewModel.getHolding()
        viewModel.getMonthOrder()
    }

    val krwBalance = remember(accountUiState) {
        when (accountUiState) {
            is GetAccountUiState.Success -> (accountUiState as GetAccountUiState.Success).account.krwBalance
            else -> 0
        }
    }

    val mockHoldingNewsModel = HoldingNewsModel(
        author = "이데일리",
        title = "애플, 사상 최고가... 올해 세계경제 2.6% 성장 전망",
        img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
    )

    AccountScreen(
        krwBalance = krwBalance,
        getHoldingListData = holdingUiState,
        getCurrentCryptoPriceData = currentCryptoPriceUiState,
        getMonthOrderData = monthOrderUiState,
        holdingNewsModel = mockHoldingNewsModel,
        navigateToChart = navigateToChart
    )
}

@Composable
private fun AccountScreen(
    modifier: Modifier = Modifier,
    krwBalance: Long,
    getHoldingListData: GetHoldingUiState,
    getCurrentCryptoPriceData: GetCurrentCryptoPriceUiState,
    getMonthOrderData: GetMonthOrderUiState,
    holdingNewsModel: HoldingNewsModel,
    navigateToChart: (marketCode: String, name: String, type: String, quantity: Int, money: Long, krwBalance: Long) -> Unit
) {
    val scrollState = rememberScrollState()

    JusicoolTheme { colors, typography ->
        Column(modifier = modifier
            .fillMaxSize()
            .background(colors.white)
        ) {
            JusicoolTopBar(
                startIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.union),
                        contentDescription = "Jusicool Logo",
                        modifier = Modifier
                            .width(116.dp)
                            .height(16.dp)
                    )
                },
                endIcon = {}
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            modifier = Modifier.JusicoolClickable { /*TODO()*/ },
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "내 자산",
                                color = colors.black,
                                style = typography.bodyMedium
                            )
                            RightArrowIcon()
                        }

                        Text(
                            text = "${krwBalance.formatMoney()}원",
                            color = colors.black,
                            style = typography.titleSmall
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "투자 자산",
                            color = colors.black,
                            style = typography.bodyMedium
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            val totalAssetValue = remember(getHoldingListData, getCurrentCryptoPriceData, krwBalance) {
                                val holdingValue = when {
                                    getHoldingListData is GetHoldingUiState.Success && getCurrentCryptoPriceData is GetCurrentCryptoPriceUiState.Success -> {
                                        getHoldingListData.account.sumOf { holding ->
                                            val currentPrice = getCurrentCryptoPriceData.markets.find { it.marketCode == holding.marketCode }?.currentPrice ?: 0.0
                                            (holding.quantity * currentPrice).toLong()
                                        }
                                    }
                                    else -> 0L
                                }
                                (krwBalance + holdingValue).formatMoney()
                            }

                            Text(
                                text = "${totalAssetValue}원",
                                color = colors.black,
                                style = typography.titleMedium
                            )

                            val profitAndRate = remember(getHoldingListData, getCurrentCryptoPriceData) {
                                if (getHoldingListData is GetHoldingUiState.Success && getCurrentCryptoPriceData is GetCurrentCryptoPriceUiState.Success) {
                                    var totalInvestment = 0.0
                                    var totalCurrentValue = 0.0

                                    getHoldingListData.account.forEach { holding ->
                                        val currentPrice = getCurrentCryptoPriceData.markets.find { it.marketCode == holding.marketCode }?.currentPrice ?: 0.0
                                        val invested = holding.price * holding.quantity
                                        val current = currentPrice * holding.quantity
                                        totalInvestment += invested
                                        totalCurrentValue += current
                                    }

                                    val profit = totalCurrentValue - totalInvestment
                                    val rate = if (totalInvestment != 0.0) (profit / totalInvestment * 100).toFloat() else 0f

                                    Pair(profit.toLong(), rate)
                                } else {
                                    Pair(0L, 0f)
                                }
                            }


                            val (profit, rate) = profitAndRate
                            val profitText ="${profit.toSignedFormattedText()}원"
                            val rateText = "(${rate.formatPercent()})"

                            Text(
                                text = "$profitText $rateText",
                                color = if (profit > 0) colors.chartPriceIncreased else colors.chartPriceDecreased,
                                style = typography.bodySmall
                            )
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "보유 주식&코인",
                                color = colors.black,
                                style = typography.subTitle
                            )

                            when (getHoldingListData) {
                                is GetHoldingUiState.Success -> {
                                    AssetList(
                                        krwBalance = krwBalance,
                                        holdings = getHoldingListData.account.toPersistentList(),
                                        getCurrentCryptoPriceData = getCurrentCryptoPriceData,
                                        navigateToChart = navigateToChart
                                    )
                                }
                                is GetHoldingUiState.Loading -> {
                                    // 보유 자산 로딩 중 UI
                                }
                                is GetHoldingUiState.Error -> {
                                    // 보유 자산 실패 UI
                                }
                            }

                        }

                        Spacer(
                            modifier = Modifier
                                .background(colors.gray100)
                                .fillMaxWidth()
                                .height(1.dp)
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "주문 내역",
                                    color = colors.black,
                                    style = typography.bodySmall
                                )

                                Row(
                                    modifier = Modifier.JusicoolClickable { /*TODO()*/ },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val orderCount = when (getMonthOrderData) {
                                        is GetMonthOrderUiState.Success -> getMonthOrderData.account.orderCount.formatMoney()
                                        else -> 0
                                    }

                                    Text(
                                        text = "이번 달 ${orderCount}건",
                                        color = colors.gray600,
                                        style = typography.bodySmall
                                    )

                                    RightArrowIcon(
                                        modifier = Modifier.size(24.dp),
                                        tint = colors.gray400
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "이번 달 수익",
                                    color = colors.black,
                                    style = typography.bodySmall
                                )

                                Row(
                                    modifier = Modifier.JusicoolClickable { /*TODO()*/ },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val monthProfitText = when (getMonthOrderData) {
                                        is GetMonthOrderUiState.Success -> {
                                            val profit = getMonthOrderData.account.rate
                                            "${profit.toSignedFormattedText()}원"
                                        }
                                        else -> "0원"
                                    }

                                    Text(
                                        text = monthProfitText,
                                        color = colors.gray600,
                                        style = typography.bodySmall
                                    )

                                    RightArrowIcon(
                                        modifier = Modifier.size(24.dp),
                                        tint = colors.gray400
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .background(colors.gray50)
                )

                HoldingNewsCard(holdingNewsModel = holdingNewsModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    val mockHoldingNewsModel = HoldingNewsModel(
        author = "이데일리",
        title = "애플, 사상 최고가... 올해 세계경제 2.6% 성장 전망",
        img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
    )

    val mockHoldings = listOf(
        HoldingModel(
            id = 1,
            marketId = 101,
            koreanName = "삼성전자",
            englishName = "Samsung Electronics",
            marketCode = "005930.KQ",
            marketType = "stock",
            quantity = 10,
            price = 70000
        ),
        HoldingModel(
            id = 2,
            marketId = 202,
            koreanName = "비트코인",
            englishName = "Bitcoin",
            marketCode = "BTC",
            marketType = "crypto",
            quantity = 2,
            price = 55000000
        )
    )

    val mockUiState = GetHoldingUiState.Success(mockHoldings)

    val mockCryptoPriceUiState = GetCurrentCryptoPriceUiState.Success(
        markets = listOf(
            CurrentCryptoHoldingPrice(
                marketCode = "weqwe",
                currentPrice = 12.00,
                priceVariation= 12,
                priceVariationPercent= 12.00,
                totalVariation = 1,
                totalValue = 1
            ),
            CurrentCryptoHoldingPrice(
                marketCode = "weqwe",
                currentPrice = 12.00,
                priceVariation= 12,
                priceVariationPercent= 12.00,
                totalVariation = 1,
                totalValue = 1
            )
        )
    )

    val mockMonthOrderUiState = GetMonthOrderUiState.Success(
        account = OrderModel(
            orderCount = 12,
            rate = 35000
        )
    )

    AccountScreen(
        krwBalance = 100000,
        getHoldingListData = mockUiState,
        getCurrentCryptoPriceData = mockCryptoPriceUiState,
        getMonthOrderData = mockMonthOrderUiState,
        holdingNewsModel = mockHoldingNewsModel,
        navigateToChart = { marketCode, name,type, quantity, money, krwBalance -> }
    )
}
