package com.meister.orderhistory.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.orderHistory.OrderHistory
import com.jusicool.entity.orderHistory.OrderHistoryType
import com.jusicool.usecase.order.GetOrderHistoryUseCase
import com.jusicool.utils.Logger
import com.meister.orderhistory.viewModel.uiState.CompletedOrderHistoryUiState
import com.meister.orderhistory.viewModel.uiState.ReservedOrderHistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class OrderHistoryViewModel @Inject constructor(
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase
) : ViewModel() {

    private val reservedRefreshTrigger = MutableStateFlow(0)

    internal val reservedOrderHistoryUiState: StateFlow<ReservedOrderHistoryUiState> =
        reservedRefreshTrigger
            .flatMapLatest {
                getOrderHistoryUseCase(OrderHistoryType.RESERVE)
                    .map<List<OrderHistory>, ReservedOrderHistoryUiState> {
                        ReservedOrderHistoryUiState(
                            isLoading = false,
                            reservedOrderData = it.toPersistentList(),
                            errorMessage = null
                        )
                    }
                    .onStart {
                        Logger.d("reservedOrderHistoryUiState", "데이터 로딩 시작")
                        emit(ReservedOrderHistoryUiState(isLoading = true))
                    }
                    .catch { e ->
                        Logger.e("reservedOrderHistoryUiState", "에러 발생: ${e.message}", e)
                        emit(
                            ReservedOrderHistoryUiState(
                                isLoading = false,
                                errorMessage = e.message
                            )
                        )
                    }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ReservedOrderHistoryUiState(isLoading = true)
            )


    internal fun refreshReservedOrders() {
        reservedRefreshTrigger.value += 1
        Logger.d("refreshReservedOrders", "Count : ${reservedRefreshTrigger.value}")
    }

    private val completedRefreshTrigger = MutableStateFlow(0)

    internal val completedOrderHistoryUiState: StateFlow<CompletedOrderHistoryUiState> =
        completedRefreshTrigger
            .flatMapLatest {
                getOrderHistoryUseCase(OrderHistoryType.COMPLETED)
                    .map<List<OrderHistory>, CompletedOrderHistoryUiState> {
                        CompletedOrderHistoryUiState(
                            isLoading = false,
                            completedOrderData = it.toPersistentList(),
                            errorMessage = null
                        )
                    }
                    .onStart {
                        Logger.d("completedOrderHistoryUiState", "데이터 로딩 시작")
                        emit(CompletedOrderHistoryUiState(isLoading = true))
                    }
                    .catch { e ->
                        Logger.e("completedOrderHistoryUiState", "에러 발생: ${e.message}", e)
                        emit(
                            CompletedOrderHistoryUiState(
                                isLoading = false,
                                errorMessage = e.message
                            )
                        )
                    }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                CompletedOrderHistoryUiState(isLoading = true)
            )

    internal fun refreshCompletedOrders() {
        completedRefreshTrigger.value += 1
        Logger.d("refreshCompletedOrders", "Count : ${completedRefreshTrigger.value}")
    }
}
