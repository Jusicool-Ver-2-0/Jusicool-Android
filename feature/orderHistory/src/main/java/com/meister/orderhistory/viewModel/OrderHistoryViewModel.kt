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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OrderHistoryViewModel @Inject constructor(
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase
) : ViewModel() {

    private val reservedRefreshTrigger = MutableSharedFlow<Unit>()

    val reservedOrderHistoryUiState: StateFlow<ReservedOrderHistoryUiState> =
        reservedRefreshTrigger
            .onStart { emit(Unit) }
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
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = ReservedOrderHistoryUiState(isLoading = true)
            )

    fun refreshReservedOrders() {
        viewModelScope.launch {
            reservedRefreshTrigger.emit(Unit)
        }
    }


    private val completedRefreshTrigger = MutableSharedFlow<Unit>()

    val completedOrderHistoryUiState: StateFlow<CompletedOrderHistoryUiState> =
        completedRefreshTrigger
            .onStart { emit(Unit) }
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
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = CompletedOrderHistoryUiState(isLoading = true)
            )

    fun refreshCompletedOrders() {
        viewModelScope.launch {
            completedRefreshTrigger.emit(Unit)
        }
    }
}
