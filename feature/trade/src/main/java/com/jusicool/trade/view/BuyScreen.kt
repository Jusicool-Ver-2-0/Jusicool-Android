package com.jusicool.trade.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.design_system.component.button.JusicoolFilledButton
import com.jusicool.design_system.component.button.state.ButtonState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.JusicoolTextField
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.trade.view.enum.TradeType
import com.jusicool.trade.viewModel.TradeViewModel
import com.jusicool.trade.viewModel.uiState.BuyUiState
import com.jusicool.utils.formatMoney
import com.jusicool.design_system.icon.LeftClarityArrowLineIcon

@Composable
fun BuyRoute(
    name: String,
    type: String,
    price: Long,
    krwBalance: Long,
    marketCode: String,
    navigateToTradeCompleted: (String, Int, TradeType, String, Int) -> Unit,
    popUpBackStack: () -> Unit,
    viewModel: TradeViewModel = hiltViewModel()
) {
    val quantity by viewModel.quantity.collectAsStateWithLifecycle()
    val buyUiState by viewModel.buyUiState.collectAsStateWithLifecycle()

    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(buyUiState) {
        if (buyUiState is BuyUiState.Success) {
            val priceValue = (buyUiState as BuyUiState.Success).price.price
            navigateToTradeCompleted(
                name,
                quantity.toIntOrNull() ?: 0,
                TradeType.BUY,
                type,
                priceValue
            )
        }
    }

    BuyScreen(
        type = type,
        price = price,
        krwBalance = krwBalance,
        quantity = quantity,
        marketCode = marketCode,
        onQuantityChange = viewModel::onQuantityChange,
        isError = isError,
        setError = { isError = it },
        popUpBackStack = popUpBackStack,
        onBuyClick = viewModel::onBuyClick
    )
}

@Composable
fun BuyScreen(
    modifier: Modifier = Modifier,
    type: String,
    price: Long,
    krwBalance: Long,
    quantity: String,
    marketCode: String,
    isError: Boolean,
    onQuantityChange: (String) -> Unit,
    setError: (Boolean) -> Unit,
    popUpBackStack: () -> Unit,
    onBuyClick: (String) -> Unit
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            val maxBuyAble = (krwBalance / price).coerceAtLeast(0L)

            val typeText = if (type == "CRYPTO") "코인" else "주식"
            val unitText = if (type == "CRYPTO") "개" else "주"

            JusicoolTopBar(
                startIcon = {
                    LeftClarityArrowLineIcon(
                        modifier = Modifier.JusicoolClickable { popUpBackStack() }
                    )
                },
                betweenText = "$typeText 구매",
                endIcon = { Spacer(modifier = Modifier.size(24.dp)) }
            )

            Spacer(modifier = Modifier.size(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                JusicoolTextField(
                    label = "몇 $unitText 구매할까요?",
                    textState = quantity,
                    onTextChange = {
                        if (it.all { char -> char.isDigit() }) {
                            onQuantityChange(it)
                        }
                    },
                    placeHolder = "최대 ${maxBuyAble.formatMoney()}$unitText 구매 가능",
                    helperText = if (isError) "" else "보유 금액: ${krwBalance.formatMoney()}원",
                    errorText = if (isError) "보유 금액이 부족합니다" else "",
                    isError = isError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                val isInputValid = (quantity.toLongOrNull() ?: 0L) > 0L

                JusicoolFilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    text = "구매하기",
                    state = if (isInputValid) ButtonState.Enable else ButtonState.Disable,
                    onClick = {
                        val totalCost = quantity.toLongOrNull()?.let { it * price } ?: 0L
                        val hasError = totalCost > krwBalance
                        setError(hasError)
                        if (!hasError) {
                            onBuyClick(marketCode)
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BuyScreenPreview() {
    BuyScreen(
        type = "CRYPTO",
        price = 10000L,
        quantity = "",
        krwBalance = 1,
        marketCode = "",
        onQuantityChange = {},
        isError = false,
        setError = {},
        popUpBackStack = {},
        onBuyClick = {}
    )
}
