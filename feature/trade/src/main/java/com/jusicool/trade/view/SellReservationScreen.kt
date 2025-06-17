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
import com.school_of_company.design_system.icon.ClarityArrowLineIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SellReserveRoute(
    type: String,
    popUpBackStack: () -> Unit
) {
    var quantity by remember { mutableStateOf("") }
    var reservePrice by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState { 2 }
    val coroutine = rememberCoroutineScope()

    SellReserveScreen (
        type = type,
        quantity = quantity,
        pagerState = pagerState,
        coroutineScope = coroutine,
        onQuantityChange = {
            quantity = it
            isError = false
        },
        reservePrice = reservePrice,
        onReservePriceChange = { reservePrice = it },
        isError = isError,
        setError = { isError = it },
        popUpBackStack = popUpBackStack
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SellReserveScreen(
    modifier: Modifier = Modifier,
    type: String,
    quantity: String,
    reservePrice:String,
    isError: Boolean,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    onQuantityChange: (String) -> Unit,
    onReservePriceChange: (String) -> Unit,
    setError: (Boolean) -> Unit,
    popUpBackStack: () -> Unit
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        )  {
            val availableCount = 10L

            val titleText = if (type.uppercase() == "CRYPTO") "코인" else "주식"
            val unitText = if (type.uppercase() == "CRYPTO") "개" else "주"
            val unitLabel = "몇 $unitText 판매할까요?"

            JusicoolTopBar(
                startIcon = {
                    ClarityArrowLineIcon(modifier = Modifier.JusicoolClickable { popUpBackStack() })
                },
                betweenText = "$titleText 판매",
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
                        val isInputValid = reservePrice.isNotEmpty()

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
                                label = unitLabel,
                                textState = quantity,
                                onTextChange = {
                                    if (it.all { char -> char.isDigit() }) {
                                        onQuantityChange(it)
                                    }
                                },
                                placeHolder = "최대 $availableCount$unitText 판매 가능",
                                helperText = if (isError) "" else "보유 $titleText: $availableCount$unitText",
                                errorText = if (isError) "보유 ${titleText}이 부족합니다" else "",
                                isError = isError,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )

                            val isInputValid = quantity.isNotEmpty()

                            JusicoolFilledButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                text = "판매하기",
                                state = if (isInputValid) ButtonState.Enable else ButtonState.Disable,
                                onClick = {
                                    val count = quantity.toLongOrNull() ?: 0L
                                    val hasError = availableCount < count
                                    setError(hasError)
                                    if (!hasError) {
                                        // 판매 처리
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
        type = "CRYPTO",
        quantity = "",
        pagerState = pagerState,
        coroutineScope = coroutineScope,
        reservePrice = "",
        onReservePriceChange = { },
        onQuantityChange = {},
        isError = false,
        setError = {},
        popUpBackStack = {}
    )
}
