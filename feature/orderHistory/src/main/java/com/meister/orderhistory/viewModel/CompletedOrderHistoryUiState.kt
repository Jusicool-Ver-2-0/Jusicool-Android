package com.meister.orderhistory.viewModel

import com.jusicool.entity.orderHistory.OrderHistory
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class CompletedOrderHistoryUiState(
    val isLoading: Boolean = true,
    val completedOrderData: PersistentList<OrderHistory> = persistentListOf(),
    val errorMessage: String? = null,
)