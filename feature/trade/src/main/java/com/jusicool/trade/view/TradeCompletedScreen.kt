package com.jusicool.trade.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.trade.view.enum.TradeType
import com.jusicool.trade.viewModel.TradeViewModel
import com.jusicool.design_system.icon.LeftClarityArrowLineIcon
import com.jusicool.design_system.icon.RightClarityArrowLineIcon
import com.jusicool.design_system.icon.TradeSuccessImage

@Composable
fun TradeCompletedRoute(
    name: String,
    quantity: Int,
    tradeType: TradeType,
    investmentType: String,
    price: Int,
    navigateToAccount: () -> Unit,
    navigateToOrderHistory: () -> Unit,
    viewModel: TradeViewModel = hiltViewModel()
) {
    TradeCompletedScreen(
        name = name,
        quantity = quantity,
        price = price,
        tradeType = tradeType,
        investmentType = investmentType,
        navigateToAccount = navigateToAccount,
        navigateToOrderHistory = navigateToOrderHistory
    )
}

@Composable
fun TradeCompletedScreen(
    modifier: Modifier = Modifier,
    name: String,
    quantity: Int,
    price: Int,
    tradeType: TradeType,
    investmentType: String,
    navigateToAccount: () -> Unit,
    navigateToOrderHistory: () -> Unit
) {
    val tradeTypeText = when (tradeType) {
        TradeType.BUY -> {
            "구매"
        }
        TradeType.SELL -> {
            "판매"
        }
        TradeType.BUYRESERVE -> {
            "구매 예약"
        }
        TradeType.SELLRESERVE -> {
            "판매 예약"
        }
    }

    val investmentTypeText = if (investmentType == "CRYPTO") "코인" else "주식"
    val investmentUnitText = if (investmentType == "CRYPTO") "개" else "주"

    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white),
        ) {
            JusicoolTopBar(
                startIcon = {
                    LeftClarityArrowLineIcon(
                        modifier = Modifier.JusicoolClickable { navigateToAccount() }
                    )
                },
                betweenText = "$investmentTypeText $tradeTypeText",
                endIcon = { Spacer(modifier = Modifier.size(24.dp)) }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TradeSuccessImage(modifier = Modifier.size(177.dp))

                Spacer(modifier = Modifier.height(31.dp))

                Text(
                    text = "$name ${quantity}$investmentUnitText\n${price}원 $tradeTypeText 성공",
                    color = colors.black,
                    style = typography.subTitle,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.JusicoolClickable { navigateToOrderHistory() },
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "주문 내역 보러가기",
                        color = colors.gray600,
                        style = typography.bodySmall
                    )

                    RightClarityArrowLineIcon()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TradeCompletedScreenPreview() {
    TradeCompletedScreen(
        name = "마이코르소프트",
        quantity = 1,
        price = 1000,
        tradeType = TradeType.BUY,
        investmentType = "CRYPTO",
        navigateToAccount = {},
        navigateToOrderHistory = {}
    )
}