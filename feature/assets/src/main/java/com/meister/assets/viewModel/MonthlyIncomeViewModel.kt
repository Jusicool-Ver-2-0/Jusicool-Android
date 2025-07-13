package com.meister.assets.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.usecase.account.GetAccountResponseUseCase
import com.jusicool.usecase.holding.GetHoldingResponseUseCase
import com.jusicool.usecase.order.GetMonthOrderUseCase
import com.meister.assets.viewModel.uiState.MonthlyIncomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

@HiltViewModel
class MonthlyIncomeViewModel @Inject constructor(
    getHoldingResponseUseCase: GetHoldingResponseUseCase,
    getAccountResponseUseCase: GetAccountResponseUseCase,
    getMonthOrderUseCase: GetMonthOrderUseCase,
) : ViewModel() {

    internal val uiState: StateFlow<MonthlyIncomeUiState> = combine(
        getHoldingResponseUseCase(),    // Flow<HoldingType>
        getAccountResponseUseCase(),    // Flow<AccountModel>
        getMonthOrderUseCase()          // Flow<OrderModel>
    ) { holdingType, accountModel, orderModel ->
        val stockHoldings = holdingType.stockHoldings

        val investedAmount = stockHoldings.sumOf { it.totalValue() }

        val ownedStocks = stockHoldings.map { it.market.market to it.quantity }.toPersistentList()

        MonthlyIncomeUiState(
            isLoading = false,
            myMoney = accountModel.krwBalance.toInt(),
            moneyChangeFromLastMonth = orderModel.rate,
            availableOrderAmount = accountModel.krwBalance.toInt(),
            investedAmount = investedAmount,
            ownedStocks = ownedStocks
        )
    }.catch { e ->
        emit(
            MonthlyIncomeUiState(
                isLoading = false,
                errorMessage = e.message ?: "데이터를 불러오는 중 오류가 발생했습니다"
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MonthlyIncomeUiState(isLoading = true)
    )

}
