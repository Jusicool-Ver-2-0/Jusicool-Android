package com.jusicool.trade.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.component.button.JusicoolFilledButton
import com.jusicool.design_system.component.button.state.ButtonState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.JusicoolTextField
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.formatMoney
import com.school_of_company.design_system.icon.ClarityArrowLineIcon

@Composable
fun BuyRoute(
    type: String,
    price: Long,
    popUpBackStack: () -> Unit
) {
    var quantity by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    BuyScreen(
        type = type,
        price = price,
        quantity = quantity,
        onQuantityChange = {
            quantity = it
            isError = false
        },
        isError = isError,
        setError = { isError = it },
        popUpBackStack = popUpBackStack
    )
}


@Composable
fun BuyScreen(
    modifier: Modifier = Modifier,
    type: String,
    price: Long,
    quantity: String,
    onQuantityChange: (String) -> Unit,
    isError: Boolean,
    setError: (Boolean) -> Unit,
    popUpBackStack: () -> Unit
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            val availableMoney = 100000L
            val maxBuyAble = (availableMoney / price).coerceAtLeast(0L)

            val typeText = if (type == "CRYPTO") "코인" else "주식"
            val unitLabel = if (type == "CRYPTO") "몇 개 구매할까요?" else "몇 주 구매할까요?"
            val unitText = if (type == "CRYPTO") "개" else "주"

            JusicoolTopBar(
                startIcon = {
                    ClarityArrowLineIcon(
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
                    label = unitLabel,
                    textState = quantity,
                    onTextChange = {
                        if (it.all { char -> char.isDigit() }) {
                            onQuantityChange(it)
                        }
                    },
                    placeHolder = "최대 ${maxBuyAble.formatMoney()}$unitText 구매 가능",
                    helperText = if (isError) "" else "보유 금액: ${availableMoney.formatMoney()}원",
                    errorText = if (isError) "보유 금액이 부족합니다" else "",
                    isError = isError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                val isInputValid = quantity.isNotEmpty()

                JusicoolFilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    text = "구매하기",
                    state = if (isInputValid) ButtonState.Enable else ButtonState.Disable,
                    onClick = {
                        val totalCost = quantity.toLongOrNull()?.let { it * price } ?: 0L
                        val hasError = totalCost > availableMoney
                        setError(hasError)
                        if (!hasError) {
                            // 구매 실행 로직 (ex: ViewModel 등에서 수행)
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
        onQuantityChange = {},
        isError = false,
        setError = {},
        popUpBackStack = {}
    )
}
