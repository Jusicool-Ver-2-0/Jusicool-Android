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
import com.jusicool.trade.viewModel.uiState.BuyReserveUiState
import com.jusicool.trade.viewModel.uiState.SellReserveUiState
import com.school_of_company.design_system.icon.LeftClarityArrowLineIcon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SellReserveRoute(
    name: String,
    type: String,
    quantity: Int,
    marketCode: String,
    navigateToTradeCompleted: (String, Int, TradeType, String, Int) -> Unit,
    popUpBackStack: () -> Unit,
    viewModel: TradeViewModel = hiltViewModel()
) {
    val inputQuantity by viewModel.quantity.collectAsStateWithLifecycle()
    val reservePrice by viewModel.price.collectAsStateWithLifecycle()
    val sellReserveUiState by viewModel.sellReserveUiState.collectAsStateWithLifecycle()

    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(sellReserveUiState) {
        if (sellReserveUiState is SellReserveUiState.Success) {
            navigateToTradeCompleted(
                name,
                inputQuantity.toIntOrNull() ?: 0,
                TradeType.SELLRESERVE,
                type,
                (inputQuantity.toInt() * reservePrice.toInt())
            )
        }
    }

    SellReserveScreen (
        type = type,
        quantity =quantity,
        marketCode = marketCode,
        inputQuantity = inputQuantity,
        onQuantityChange = viewModel::onQuantityChange,
        reservePrice = reservePrice,
        onReservePriceChange = viewModel::onPriceChange,
        isError = isError,
        setError = { isError = it },
        onSellReserveClick = viewModel::onSellReserveClick,
        popUpBackStack = popUpBackStack
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SellReserveScreen(
    modifier: Modifier = Modifier,
    type: String,
    quantity: Int,
    inputQuantity: String,
    reservePrice:String,
    marketCode: String,
    isError: Boolean,
    onQuantityChange: (String) -> Unit,
    onReservePriceChange: (String) -> Unit,
    setError: (Boolean) -> Unit,
    onSellReserveClick: (String) -> Unit,
    popUpBackStack: () -> Unit
) {
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()

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
                                        onSellReserveClick(marketCode)
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


@Preview(showBackground = true)
@Composable
fun SellReserveScreenPreview() {
    SellReserveScreen(
        type = "CRYPTO",
        quantity = 10,
        inputQuantity = "",
        reservePrice = "",
        marketCode = "",
        onReservePriceChange = { },
        onQuantityChange = {},
        isError = false,
        setError = {},
        onSellReserveClick = {},
        popUpBackStack = {}
    )
}
