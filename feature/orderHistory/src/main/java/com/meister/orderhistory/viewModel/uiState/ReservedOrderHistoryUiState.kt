package com.meister.orderhistory.viewModel.uiState

import com.jusicool.entity.orderHistory.OrderHistory
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ReservedOrderHistoryUiState(
    val isLoading: Boolean = true,
    val reservedOrderData: PersistentList<OrderHistory> = persistentListOf(),
    val errorMessage: String? = null,
)