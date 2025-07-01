package com.jusicool.trade.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.jusicool.trade.viewModel.uiState.BuyUiState
import com.jusicool.utils.formatMoney
import com.school_of_company.design_system.icon.LeftClarityArrowLineIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BuyReserveRoute(
    name: String,
    type: String,
    price: Long,
    krwBalance:Long,
    marketCode: String,
    navigateToTradeCompleted: (String, Int, TradeType, String, Int) -> Unit,
    popUpBackStack: () -> Unit,
    viewModel: TradeViewModel = hiltViewModel()
) {
    val quantity by viewModel.quantity.collectAsStateWithLifecycle()
    val reservePrice by viewModel.price.collectAsStateWithLifecycle()
    val buyReserveUiState by viewModel.buyReserveUiState.collectAsStateWithLifecycle()

    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(buyReserveUiState) {
        if (buyReserveUiState is BuyReserveUiState.Success) {
            navigateToTradeCompleted(
                name,
                quantity.toIntOrNull() ?: 0,
                TradeType.BUYRESERVE,
                type,
                (quantity.toInt() * reservePrice.toInt())
            )
        }
    }

    BuyReserveScreen(
        type = type,
        price = price,
        quantity = quantity,
        krwBalance = krwBalance,
        marketCode = marketCode,
        onQuantityChange = viewModel::onQuantityChange,
        reservePrice = reservePrice,
        onReservePriceChange = viewModel::onPriceChange,
        isError = isError,
        setError = { isError = it },
        onBuyReserveClick = viewModel::onBuyReserveClick,
        popUpBackStack = popUpBackStack
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BuyReserveScreen(
    modifier: Modifier = Modifier,
    type: String,
    price: Long,
    krwBalance: Long,
    quantity: String,
    reservePrice: String,
    marketCode: String,
    isError: Boolean,
    setError: (Boolean) -> Unit,
    onQuantityChange: (String) -> Unit,
    onReservePriceChange: (String) -> Unit,
    onBuyReserveClick: (String) -> Unit,
    popUpBackStack: () -> Unit
) {
    val maxBuyAble = (krwBalance / price).coerceAtLeast(0L)

    val typeText = if (type == "CRYPTO") "코인" else "주식"
    val unitText = if (type == "CRYPTO") "개" else "주"

    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()

    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            JusicoolTopBar(
                startIcon = {
                    LeftClarityArrowLineIcon(
                        modifier = Modifier.JusicoolClickable {
                            if (pagerState.currentPage == 0) popUpBackStack()
                            else coroutineScope.launch { pagerState.animateScrollToPage(0) }
                        }
                    )
                },
                betweenText = "$typeText 구매 예약",
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
                                placeHolder = "예약 금액을 달성했을 때 주식을 구매해요",
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
                        val isInputValid = (quantity.toLongOrNull() ?: 0L) > 0L

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
                                        onBuyReserveClick(marketCode)
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
fun BuyReserveScreenPreview() {
    BuyReserveScreen(
        type = "CRYPTO",
        price = 10000L,
        quantity = "",
        krwBalance = 1,
        marketCode = "",
        onQuantityChange = {},
        reservePrice = "",
        onReservePriceChange = {},
        isError = false,
        setError = {},
        onBuyReserveClick = {},
        popUpBackStack = {}
    )
}
