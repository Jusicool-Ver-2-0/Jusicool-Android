package com.jusicool.trade.view

import androidx.compose.foundation.background
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
import com.jusicool.trade.view.enum.TradeType
import com.school_of_company.design_system.icon.LeftClarityArrowLineIcon

@Composable
fun SellRoute(
    name: String,
    type: String,
    quantity: Int,
    navigateToTradeCompleted: (String, Int, TradeType, String) -> Unit,
    popUpBackStack: () -> Unit
) {
    var inputQuantity  by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    SellScreen(
        name = name,
        type = type,
        quantity = quantity,
        inputQuantity  = inputQuantity ,
        onQuantityChange = {
            inputQuantity  = it
            isError = false
        },
        isError = isError,
        setError = { isError = it },
        navigateToTradeCompleted = navigateToTradeCompleted,
        popUpBackStack = popUpBackStack
    )
}

@Composable
fun SellScreen(
    modifier: Modifier = Modifier,
    name: String,
    type: String,
    quantity: Int,
    inputQuantity : String,
    onQuantityChange: (String) -> Unit,
    isError: Boolean,
    setError: (Boolean) -> Unit,
    navigateToTradeCompleted: (String, Int, TradeType, String) -> Unit,
    popUpBackStack: () -> Unit
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            val titleText = if (type.uppercase() == "CRYPTO") "코인" else "주식"
            val unitText = if (type.uppercase() == "CRYPTO") "개" else "주"

            JusicoolTopBar(
                startIcon = {
                    LeftClarityArrowLineIcon(modifier = Modifier.JusicoolClickable { popUpBackStack() })
                },
                betweenText = "$titleText 판매",
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
                    label = "몇 $unitText 판매할까요?",
                    textState = inputQuantity ,
                    onTextChange = {
                        if (it.all { char -> char.isDigit() }) {
                            onQuantityChange(it)
                        }
                    },
                    placeHolder = "최대 $quantity$unitText 판매 가능",
                    helperText = if (isError) "" else "보유 $titleText: $quantity$unitText",
                    errorText = if (isError) "보유 ${titleText}이 부족합니다" else "",
                    isError = isError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                val isInputValid = (inputQuantity.toLongOrNull() ?: 0L) > 0L

                JusicoolFilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    text = "판매하기",
                    state = if (isInputValid) ButtonState.Enable else ButtonState.Disable,
                    onClick = {
                        val count = inputQuantity .toLongOrNull() ?: 0L
                        val hasError = quantity < count
                        setError(hasError)
                        if (!hasError) {
                            navigateToTradeCompleted(name, inputQuantity.toInt(), TradeType.SELL, type )
                        }
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SellScreenPreview() {
    SellScreen(
        name = "",
        type = "CRYPTO",
        quantity = 10,
        inputQuantity  = "",
        onQuantityChange = {},
        isError = false,
        setError = {},
        navigateToTradeCompleted = { _,_,_,_, ->},
        popUpBackStack = {}
    )
}
