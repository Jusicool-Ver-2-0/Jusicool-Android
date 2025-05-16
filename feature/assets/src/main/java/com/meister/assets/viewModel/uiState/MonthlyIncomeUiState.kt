package com.meister.assets.viewModel.uiState

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MonthlyIncomeUiState(
    val isLoading: Boolean = false,
    val myMoney: Int = 0, // 내 돈
    val moneyChangeFromLastMonth: Int = 0, // 지난달 대비
    val availableOrderAmount: Int = 0, // 주문 가능 금액
    val investedAmount: Int = 0, // 투자 금액
    val ownedStocks: PersistentList<Pair<String, Int>> = persistentListOf(), // 주식 리스트
    val errorMessage: String? = null
)