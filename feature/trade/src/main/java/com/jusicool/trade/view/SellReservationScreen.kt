package com.jusicool.trade.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SellReserveRoute(
    name: String,
    type: String,
    quantity: Int,
    navigateToTradeCompleted: (String, Int, TradeType, String) -> Unit,
    popUpBackStack: () -> Unit
) {
    var inputQuantity by remember { mutableStateOf("") }
    var reservePrice by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState { 2 }
    val coroutine = rememberCoroutineScope()

    SellReserveScreen (
        name = name,
        type = type,
        quantity =quantity,
        inputQuantity = inputQuantity,
        pagerState = pagerState,
        coroutineScope = coroutine,
        onQuantityChange = {
            inputQuantity = it
            isError = false
        },
        reservePrice = reservePrice,
        onReservePriceChange = { reservePrice = it },
        isError = isError,
        setError = { isError = it },
        navigateToTradeCompleted = navigateToTradeCompleted,
        popUpBackStack = popUpBackStack
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SellReserveScreen(
    modifier: Modifier = Modifier,
    name: String,
    type: String,
    quantity: Int,
    inputQuantity: String,
    reservePrice:String,
    isError: Boolean,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    onQuantityChange: (String) -> Unit,
    onReservePriceChange: (String) -> Unit,
    setError: (Boolean) -> Unit,
    navigateToTradeCompleted: (String, Int, TradeType, String) -> Unit,
    popUpBackStack: () -> Unit
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        )  {
            val titleText = if (type.uppercase() == "CRYPTO") "코인" else "주식"
            val unitText = if (type.uppercase() == "CRYPTO") "개" else "주"

            JusicoolTopBar(
                startIcon = {
                    LeftClarityArrowLineIcon(modifier = Modifier.JusicoolClickable { popUpBackStack() })
                },
                betweenText = "$titleText 판매 예약",
                endIcon = { Spacer(modifier = Modifier.size(24.dp)) }
            )

            Spacer(modifier = Modifier.size(8.dp))

            HorizontalPager(
                modifier = modifier.fillMaxSize(),
                state = pagerState,
                userScrollEnabled = false,
            ) { page ->
                when (page) {
                    0 -> {
                        val isInputValid = (reservePrice.toLongOrNull() ?: 0L) > 0L

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp, vertical = 32.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            JusicoolTextField(
                                label = "예약 금액을 입력하세요",
                                textState = reservePrice,
                                onTextChange = {
                                    if (it.all { c -> c.isDigit() }) {
                                        onReservePriceChange(it)
                                    }
                                },
                                placeHolder = "예약 금액을 달성했을 때 주식을 판매해요",
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )

                            JusicoolFilledButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                text = "다음",
                                state = if (isInputValid) ButtonState.Enable else ButtonState.Disable,
                                onClick = {
                                    if (isInputValid) {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(1)
                                        }
                                    }
                                }
                            )
                        }
                    }

                    1 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp, vertical = 32.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            JusicoolTextField(
                                label = "몇 $unitText 판매할까요?",
                                textState = inputQuantity,
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
                                    val count = inputQuantity.toLongOrNull() ?: 0L
                                    val hasError = quantity < count
                                    setError(hasError)
                                    if (!hasError) {
                                        navigateToTradeCompleted(name, inputQuantity.toInt(), TradeType.SELLRESERVE, type )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun SellReserveScreenPreview() {
    val pagerState = rememberPagerState(initialPage = 0) { 2 }
    val coroutineScope = rememberCoroutineScope()

    SellReserveScreen(
        name = "",
        type = "CRYPTO",
        quantity = 10,
        inputQuantity = "",
        pagerState = pagerState,
        coroutineScope = coroutineScope,
        reservePrice = "",
        onReservePriceChange = { },
        onQuantityChange = {},
        isError = false,
        setError = {},
        navigateToTradeCompleted = { _,_,_,_, ->},
        popUpBackStack = {}
    )
}
